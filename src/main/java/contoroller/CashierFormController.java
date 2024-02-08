package contoroller;

import bo.BoFactory;
import bo.custom.InventoryBo;
import bo.custom.OrderBo;
import bo.util.BoType;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustIDOrderDto;
import dto.InventoryDto;
import dto.tm.InventoryTm2;
import dto.tm.OrdersTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CashierFormController {
    public JFXTextField txtCustomerID;
    public JFXTextField txtOrderID;

    //ordertable
    public TreeTableColumn colOrderId;
    public TreeTableColumn colCustomerId;
    public TreeTableColumn colDate;
    public TreeTableColumn colStatus;

    //inventory
    public TreeTableColumn colName;
    public TreeTableColumn colCategory;
    public TreeTableColumn colIStatus;

    //parts
    public TreeTableColumn colParts;
    public TreeTableColumn colCost;


    public Label lblbtotalparts;
    public JFXTextField txtservicecost;

    public Label lblTotal;
    public JFXTreeTableView ordersTable;
    public JFXTreeTableView inventoryTable;
    public JFXTreeTableView partsTable;

    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private InventoryBo inventoryBo = BoFactory.getInstance().getBo(BoType.INVENTORY);
    public void initialize(){
        colOrderId.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerID"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));

        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("Name"));
        colCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("Category"));
        colIStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("Status"));

        colParts.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colCost.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));

        LoadOrderTable();

        ordersTable.setOnMouseClicked(event -> {
            if(event.getClickCount()==1&&(!ordersTable.getSelectionModel().isEmpty())){
                TreeItem<OrdersTm> item = (TreeItem<OrdersTm>) ordersTable.getSelectionModel().getSelectedItem();
                ObservableList<InventoryTm2> tmList = FXCollections.observableArrayList();
                int numericValue  = Integer.parseInt(item.getValue().getOrderId().replaceAll("\\D*(\\d+).*", "$1"));
                List<InventoryDto> dtoList = inventoryBo.getOrderDetails(numericValue);
                for(InventoryDto dto:dtoList){
                    tmList.add(
                            new InventoryTm2(
                                    dto.getName(),
                                    dto.getCategory(),
                                    dto.getStatus()
                            )
                    );
                }
                TreeItem<InventoryTm2> treeItem = new RecursiveTreeItem<>(tmList,RecursiveTreeObject::getChildren);
                inventoryTable.setRoot(treeItem);
                inventoryTable.setShowRoot(false);
            }
        });
        inventoryTable.setOnMouseClicked(event -> {
            if(event.getClickCount()==1&&(!inventoryTable.getSelectionModel().isEmpty())){
                TreeItem<InventoryTm2> item = (TreeItem<InventoryTm2>) inventoryTable.getSelectionModel().getSelectedItem();
                ObservableList<InventoryTm2> tmList = FXCollections.observableArrayList();
                int numericValue  = Integer.parseInt(item.getValue().getId().replaceAll("\\D*(\\d+).*", "$1"));
                List<InventoryDto> dtoList = inventoryBo.getOrderDetails(numericValue);
                for(InventoryDto dto:dtoList){
                    tmList.add(
                            new InventoryTm2(
                                    dto.getName(),
                                    dto.getCategory(),
                                    dto.getStatus()
                            )
                    );
                }
                TreeItem<InventoryTm2> treeItem = new RecursiveTreeItem<>(tmList,RecursiveTreeObject::getChildren);
                inventoryTable.setRoot(treeItem);
                inventoryTable.setShowRoot(false);


            }
        });

    }

    private void LoadOrderTable() {
        ObservableList<OrdersTm> ordersTms = FXCollections.observableArrayList();
        List<CustIDOrderDto> custIDOrderDtos = orderBo.allOrders();
        for (CustIDOrderDto custIDOrderDto: custIDOrderDtos){
            ordersTms.add(
                    new OrdersTm(
                            custIDOrderDto.getCustID(),
                            custIDOrderDto.getCustomId(),
                            custIDOrderDto.getDate(),
                            custIDOrderDto.getStatus()
                    )
            );
        }
        TreeItem<OrdersTm> treeItem = new RecursiveTreeItem<>(ordersTms, RecursiveTreeObject::getChildren);
        ordersTable.setRoot(treeItem);
        ordersTable.setShowRoot(false);
    }

    public void backBtnonAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) txtservicecost.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFormController.fxml"))));
        stage.centerOnScreen();
        stage.show();

    }

    public void proceedbtnAction(ActionEvent actionEvent) {
    }
}
