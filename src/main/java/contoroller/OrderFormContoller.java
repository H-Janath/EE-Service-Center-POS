package contoroller;

import bo.custom.impl.InventoryBoimpl;
import bo.custom.impl.OrderBoimpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustIDOrderDto;
import dto.InventoryDto;
import dto.tm.InventoryTm2;
import dto.tm.OrdersTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class OrderFormContoller {
    public JFXTextField txtCustomerID;
    public JFXTextField txtOrderID;
    public TreeTableColumn colStatus;
    public TreeTableColumn colOption;
    public TreeTableColumn colOrderId;
    public TreeTableColumn colCustomerId;
    public TreeTableColumn colDate;
    public JFXTreeTableView<OrdersTm> ordersTable;
    public TreeTableColumn colInventoryid;
    public TreeTableColumn colName;
    public TreeTableColumn colCategory;
    public TreeTableColumn colIStatus;
    public TreeTableColumn colFault;
    public JFXTreeTableView<InventoryTm2> inventoryTable;

    OrderBoimpl orderBoimpl = new OrderBoimpl();
    InventoryBoimpl inventoryBoimpl = new InventoryBoimpl();

    public void orderidsearchBtnSetAction(ActionEvent actionEvent) {
    }

    public void customerIDsearchBtnSetAction(ActionEvent actionEvent) {
    }
    public void initialize(){
        colOrderId.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerID"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("option"));
        colStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));


        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colInventoryid.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        colIStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));
        colFault.setCellValueFactory(new TreeItemPropertyValueFactory<>("fault"));
        loadOrderTable();
        ordersTable.setOnMouseClicked(event -> {
            if(event.getClickCount()==1&&(!ordersTable.getSelectionModel().isEmpty())){
                TreeItem<OrdersTm> item = ordersTable.getSelectionModel().getSelectedItem();
                    ObservableList<InventoryTm2> tmList = FXCollections.observableArrayList();
                    int numericValue  = Integer.parseInt(item.getValue().getOrderId().replaceAll("\\D*(\\d+).*", "$1"));
                    List<InventoryDto> dtoList = inventoryBoimpl.getOrderDetails(numericValue);
                    for(InventoryDto dto:dtoList){
                        System.out.println(dto.getName());
                        tmList.add(
                                new InventoryTm2(
                                        dto.getCustomId(),
                                        dto.getName(),
                                        dto.getCategory(),
                                        dto.getFault(),
                                        dto.getStatus()
                                )
                        );
                    }
                    TreeItem<InventoryTm2> treeItem = new RecursiveTreeItem<>(tmList,RecursiveTreeObject::getChildren);
                    inventoryTable.setRoot(treeItem);
                    inventoryTable.setShowRoot(false);
            }
        });

        txtOrderID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                ordersTable.setPredicate(new Predicate<TreeItem<OrdersTm>>() {
                    @Override
                    public boolean test(TreeItem<OrdersTm> treeItem) {
                        return treeItem.getValue().getOrderId().contains(newValue) ;
                    }
                });
            }
        });
        txtCustomerID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                ordersTable.setPredicate(new Predicate<TreeItem<OrdersTm>>() {
                    @Override
                    public boolean test(TreeItem<OrdersTm> treeItem) {
                        return treeItem.getValue().getCustomerID().contains(newValue) ;
                    }
                });
            }
        });

    }

    private void loadOrderTable() {
        ObservableList<OrdersTm> ordersTms = FXCollections.observableArrayList();
        List<CustIDOrderDto> custIDOrderDtos = orderBoimpl.allOrders();
        for(CustIDOrderDto custIDOrderDto: custIDOrderDtos){
            JFXButton button = new JFXButton("More");
            ordersTms.add(
                    new OrdersTm(
                            custIDOrderDto.getCustID(),
                            custIDOrderDto.getCustomId(),
                            custIDOrderDto.getDate(),
                            custIDOrderDto.getStatus(),
                            button
                    )
            );
            button.setOnAction(ActionEvent->{
                try {
                    displayInventory(custIDOrderDto.getCustID());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        TreeItem<OrdersTm> treeItem= new RecursiveTreeItem<>(ordersTms, RecursiveTreeObject::getChildren);
        ordersTable.setRoot(treeItem);
        ordersTable.setShowRoot(false);

    }

    private void displayInventory(String customerId) throws IOException {

    }


    public void backBtnonAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) txtCustomerID.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFormController.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }
}
