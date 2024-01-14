package contoroller;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormContoller {


    @FXML
    private AnchorPane menu_pane;

    @FXML
    private Label orderIdlbl;
    @FXML
    private JFXComboBox cmbCategoryid;

    public void initialize() {
        menu_pane.setVisible(true);
    }



    public void ORDERS_btnSetOnAction(ActionEvent actionEvent) {
    }

    public void CUSTOMERS_btoSetONAction(ActionEvent actionEvent) {
    }

    public void REPORT_btnSetOnAction(ActionEvent actionEvent) {
    }

    public void USERS_btnSetOnAction(ActionEvent actionEvent) {
    }

    public void INVENTORY_btnSetOnAction(ActionEvent actionEvent) {
    }

    public void additm_btnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)menu_pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddItemForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Add item Form");
        stage.show();
    }

    public void signoutSetOnaction(ActionEvent actionEvent) {
        showConfirmationDialog();
    }
    public void showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to proceed with this action?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                Stage stage = (Stage)menu_pane.getScene().getWindow();
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/loginForm.fxml"))));
                    stage.centerOnScreen();
                    stage.setTitle("Login Form");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
