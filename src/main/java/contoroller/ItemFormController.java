package contoroller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemFormController{
    public TreeTableColumn colinventoryId;
    public TreeTableColumn colCategory;
    public TreeTableColumn colName;
    public TreeTableColumn colFault;
    public TreeTableColumn colStatus;
    public JFXTreeTableView itemsTable;
    public Label lblOrderId;


    public void initialize(){

    }

    public void backBtnonAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) itemsTable.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrdersForm.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }

}
