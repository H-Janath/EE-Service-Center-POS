package contoroller;
import bo.AddItemBoImpl;
import bo.DashboardFormBoImpl;
import com.jfoenix.controls.JFXComboBox;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardFormContoller {

    public ComboBox cmbItem;
    private AddItemBoImpl addItemBo = new AddItemBoImpl();
    private DashboardFormBoImpl dashboardFormBo = new DashboardFormBoImpl();
    @FXML
    private AnchorPane menu_pane;
    @FXML
    private ComboBox cmbCategory;

    private ObservableList<String> tricalelist = FXCollections.observableArrayList();
    private ObservableList<String> tronicList = FXCollections.observableArrayList();

    public void initialize() {
        setCategory();
        loadTronic();
        loadTricale();
        cmbCategory.getSelectionModel().selectedItemProperty().addListener((observable,oldvalue,id)->{
            if(observable.getValue().equals("ELECTRONIC")){
                cmbItem.getSelectionModel().clearSelection();
                    cmbItem.setItems(tronicList);
            }else {
                cmbItem.getSelectionModel().clearSelection();
                cmbItem.setItems(tricalelist);
            }
        } );;
    }
    private void setCategory(){
        ObservableList<String> categorylist = addItemBo.getCategory();
        cmbCategory.setItems(categorylist);
    }
    private void loadTricale() {
        List<ItemDto> tricaleItems = new ArrayList<>();
        tricaleItems = dashboardFormBo.getElectricItem();
        for (ItemDto dto : tricaleItems) {
            tricalelist.add(dto.getName());
        }
    }
    private void loadTronic() {
        List<ItemDto> tronicItem = new ArrayList<>();
        tronicItem = dashboardFormBo.getElectronicItem();
        for (ItemDto dto : tronicItem) {
            tronicList.add(dto.getName());
        }
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
