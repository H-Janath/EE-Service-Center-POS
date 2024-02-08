package contoroller;

import bo.BoFactory;
import bo.custom.OrderBo;
import bo.util.BoType;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustIDOrderDto;
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
