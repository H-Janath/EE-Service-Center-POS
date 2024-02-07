package contoroller;
import Security.EmailService;
import Security.OTP;
import bo.BoFactory;
import bo.custom.impl.UsersBoImpl;
import bo.util.BoType;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.UsersDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class passwordResetFormController {
    public JFXTextField txtEmail;
    public JFXPasswordField txtConfirmPassword;
    public JFXPasswordField txtxNewPassword;
    public JFXTextField txtOtp;

    UsersBoImpl usersBo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize(){
        txtConfirmPassword.setEditable(false);
        txtxNewPassword.setEditable(false);
    }
    public void otpLinkSetOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        UsersDto usersDto = usersBo.searchUser(email);
        if(usersDto!=null ){
            String  otp = OTP.generateOtp(email);
            EmailService.sendOtpByEmail(usersDto.getEmail(),otp);
        }else {
            new Alert(Alert.AlertType.INFORMATION,"This username not exist").show();
        }
    }

    public void cancelBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }

    public void confirmBtnSetOnAction(ActionEvent actionEvent) {
        String otp = txtOtp.getText();
        boolean isvalidOTP = OTP.verifyOtp(txtEmail.getText(),otp);
        if(isvalidOTP){
            txtConfirmPassword.setEditable(true);
            txtxNewPassword.setEditable(true);
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid OTP").show();
        }
    }

    public void resetBtnOnAction(ActionEvent actionEvent) throws NoSuchAlgorithmException {
        Boolean isSaved = usersBo.updatePassword(txtEmail.getText(),txtConfirmPassword.getText());
        if(isSaved){
            new Alert(Alert.AlertType.INFORMATION,"Reset password successfully").show();
        }
    }
}
