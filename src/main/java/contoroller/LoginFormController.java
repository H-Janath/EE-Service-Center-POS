package contoroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane paneid;
    public Label Logging;

    public void ForgetpasswordOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)Logging.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/passwordResetForm.fxml"))));
        stage.setTitle("Item Form");
        stage.show();
    }
}
