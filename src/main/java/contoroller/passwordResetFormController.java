package contoroller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class passwordResetFormController {
    public JFXTextField txtEmail;
    public JFXPasswordField txtConfirmPassword;
    public JFXPasswordField txtxNewPassword;
    public JFXTextField txtOtp;

    public void otpLinkSetOnAction(ActionEvent actionEvent) {
    }

    public void cancelBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }

    public void confirmBtnSetOnAction(ActionEvent actionEvent) {
    }

    public void resetBtnOnAction(ActionEvent actionEvent) {
    }
}
