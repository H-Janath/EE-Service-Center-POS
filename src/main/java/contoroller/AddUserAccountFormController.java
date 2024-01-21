package contoroller;

import bo.UsersBoImpl;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.UsersDto;
import dto.tm.UsersTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AddUserAccountFormController {
    public JFXPasswordField txtconfirmpassword;
    public JFXPasswordField txtenterpassword;
    public JFXComboBox cmbUsers;
    public JFXTextField txtuserName;
    public JFXTreeTableView<UsersTm> usersTable;
    public TreeTableColumn colEmail;
    public TreeTableColumn colRole;
    public TreeTableColumn colOption;
    private UsersBoImpl userAccountBo = new UsersBoImpl();

    public void initialize(){
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new TreeItemPropertyValueFactory<>("role"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("button"));
        
        loadUsersTable();
        setUserType();
    }

    private void loadUsersTable() {
        ObservableList<UsersTm> usersTms = FXCollections.observableArrayList();
        List<UsersDto> usersDtoList = userAccountBo.allUsers();
        for(UsersDto usersDto: usersDtoList){
            JFXButton button = new JFXButton("Delete");
            usersTms.add(
                    new UsersTm(
                            usersDto.getEmail(),
                            usersDto.getRole(),
                            button
                    )
            );
            button.setOnAction(ActionEvent->{
                deleteUsers(usersDto.getEmail());
            });
        }
        TreeItem<UsersTm> treeItem= new RecursiveTreeItem<>(usersTms, RecursiveTreeObject::getChildren);
        usersTable.setRoot(treeItem);
        usersTable.setShowRoot(false);

    }

    private void deleteUsers(String email) {

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

        if(!(enteredPassword.isEmpty()||confirmPassword.isEmpty()||txtuserName.getText().isEmpty()||cmbUsers.getSelectionModel().isEmpty())){
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
        }else {
            new Alert(Alert.AlertType.ERROR,"Empty field found").show();
        }

    }
    private void clearField(){
        txtconfirmpassword.clear();
        txtenterpassword.clear();
        txtuserName.clear();
        cmbUsers.getSelectionModel().clearSelection();
    }

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) cmbUsers.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFormController.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }
}