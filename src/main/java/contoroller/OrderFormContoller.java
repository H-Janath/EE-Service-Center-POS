package contoroller;
import bo.OrderBoimpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustIDOrderDto;
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
    OrderBoimpl orderBoimpl = new OrderBoimpl();

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
        loadOrderTable();

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
        }
        TreeItem<OrdersTm> treeItem= new RecursiveTreeItem<>(ordersTms, RecursiveTreeObject::getChildren);
        ordersTable.setRoot(treeItem);
        ordersTable.setShowRoot(false);

    }

    public void backBtnonAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) txtCustomerID.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFormController.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }
}
