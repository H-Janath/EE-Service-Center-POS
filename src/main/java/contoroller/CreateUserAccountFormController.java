package contoroller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class CreateUserAccountFormController {
    public JFXTextField txtEmail;
    public JFXPasswordField txtconfirmpassword;
    public JFXPasswordField txtenterpassword;

    public void confirmbtnSetOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String enteredPassword = txtenterpassword.getText();
        String confirmPassword = txtconfirmpassword.getText();

        if(enteredPassword.equals(confirmPassword)){

        }else{
            new Alert(Alert.AlertType.ERROR,"Password dosen't match").show();
            txtenterpassword.clear();
            txtconfirmpassword.clear();
        }

    }

    public void cancelBtnOnAction(ActionEvent actionEvent) {
    }
}