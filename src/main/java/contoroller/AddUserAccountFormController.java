package contoroller;
import bo.UserAccountBoImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.UsersDto;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.security.NoSuchAlgorithmException;

public class AddUserAccountFormController {
    public JFXPasswordField txtconfirmpassword;
    public JFXPasswordField txtenterpassword;
    public JFXComboBox cmbUsers;
    public JFXTextField txtuserName;
    private UserAccountBoImpl userAccountBo = new UserAccountBoImpl();

    public void initialize(){
        setUserType();
    }

    private void setUserType() {
        cmbUsers.setItems(userAccountBo.getUsers());
    }

    public void cancelBtnOnAction(ActionEvent actionEvent) {
        clearField();
    }

    public void CreateBtnSetOnAction(ActionEvent actionEvent) {
        String enteredPassword = txtenterpassword.getText();
        String confirmPassword = txtconfirmpassword.getText();

        if(enteredPassword.equals(confirmPassword)){
            try {
                if(userAccountBo.saveUser(new UsersDto(
                        txtuserName.getText(),
                        enteredPassword,
                        cmbUsers.getValue().toString()
                ))){
                    new Alert(Alert.AlertType.INFORMATION,"Successfully added").show();
                    clearField();
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        }else{
            new Alert(Alert.AlertType.ERROR,"Password dosen't match").show();
            txtenterpassword.clear();
            txtconfirmpassword.clear();
        }
    }
    private void clearField(){
        txtconfirmpassword.clear();
        txtenterpassword.clear();
        txtuserName.clear();
        cmbUsers.getSelectionModel().clearSelection();
    }
}