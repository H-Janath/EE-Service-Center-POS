package contoroller;
import bo.UsersBoImpl;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
public class LoginFormController {
    public Label Logging;
    public JFXPasswordField textPassword;
    public JFXTextField txtxUserName;
    UsersBoImpl usersBo = new UsersBoImpl();

    public void ForgetpasswordOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)Logging.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/passwordResetForm.fxml"))));
        stage.setTitle("Item Form");
        stage.show();
    }


    public void loginBtnSetOnAction(ActionEvent actionEvent) {
        String name = txtxUserName.getText();
        String password = textPassword.getText();
        try {
            boolean isValied = usersBo.checkValidityofCredintial(name,password);
            if(isValied){
                new Alert(Alert.AlertType.INFORMATION,"Login Successful").show();
                Stage stage = (Stage) txtxUserName.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFormController.fxml"))));
                stage.show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Login unsuccessful").show();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
