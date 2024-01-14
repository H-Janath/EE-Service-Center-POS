package contoroller;
import bo.AddItemBoImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;


public class AddItemFormController {
    public JFXTextField txtName;
    public JFXComboBox cmbCategory;
    public Label LBLitemcode;
    private AddItemBoImpl addItemBo = new AddItemBoImpl();
    public void initialize(){
        LBLitemcode.setText(addItemBo.genertateID());
        setCategory();
    }

    public void AddItemBtnSetOnAction(ActionEvent actionEvent) {
        if(addItemBo.saveItem(
                new ItemDto(
                        LBLitemcode.getText(),
                        txtName.getText(),
                        cmbCategory.getSelectionModel().getSelectedItem().toString()))){
            new Alert(Alert.AlertType.INFORMATION,"Save success").show();
            clearField();
        }else {
            new Alert(Alert.AlertType.ERROR,"Save Un success").show();
        }
    }
    private void clearField(){
        txtName.clear();
        cmbCategory.getSelectionModel().clearSelection();
        LBLitemcode.setText(addItemBo.genertateID());

    }

    public void CancelBtnSetOnAction(ActionEvent actionEvent) {
    }

    public void backBtnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) txtName.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFormController.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }
    private void setCategory(){
        ObservableList<String> categorylist = addItemBo.getCategory();
        cmbCategory.setItems(categorylist);
    }
}
