package contoroller;

import bo.AddItemBoImpl;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.ItemDto;
import dto.tm.ItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class AddItemFormController {
    public JFXTextField txtName;
    public JFXComboBox cmbCategory;
    public Label LBLitemcode;
    public TreeTableColumn colitemCode;
    public TreeTableColumn colName;
    public TreeTableColumn colCategory;
    public TreeTableColumn colOption;
    public JFXTreeTableView<ItemTm> itemTable;
    private AddItemBoImpl addItemBo = new AddItemBoImpl();
    public void initialize(){
        colitemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();

        LBLitemcode.setText(addItemBo.genertateID());
        setCategory();
    }

    private void loadItemTable() {
        ObservableList<ItemTm> itemTms = FXCollections.observableArrayList();
        List<ItemDto> itemDtos = addItemBo.allItems();
        if(itemDtos!=null) {
            for (ItemDto itemDto : itemDtos) {
                JFXButton button = new JFXButton("Delete");
                itemTms.add(
                        new ItemTm(
                                itemDto.getItem_code(),
                                itemDto.getName(),
                                itemDto.getCategory(),
                                button
                        )
                );
                button.setOnAction(ActionEvent -> {
                    deleteItem(itemDto.getItem_code());
                    itemTable.refresh();
                });
            }
        }
        TreeItem<ItemTm> treeItem= new RecursiveTreeItem<>(itemTms, RecursiveTreeObject::getChildren);
        itemTable.setRoot(treeItem);
        itemTable.setShowRoot(false);
    }

    private void deleteItem(String itemCode) {
        if(addItemBo.deleteItem(itemCode)){
            new Alert(Alert.AlertType.INFORMATION,"Delete Success").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Delete unsuccess").show();
        }
    }

    public void AddItemBtnSetOnAction(ActionEvent actionEvent) {
        if(!(txtName.getText().isEmpty()||cmbCategory.getSelectionModel().isEmpty())){
        addItemBo.saveItem(
                new ItemDto(
                        LBLitemcode.getText(),
                        txtName.getText(),
                        cmbCategory.getSelectionModel().getSelectedItem().toString()));
            new Alert(Alert.AlertType.INFORMATION,"Save success").show();
            clearField();
        }else{
            new Alert(Alert.AlertType.ERROR,"Save Un success").show();
        }

    }
    private void clearField(){
        txtName.clear();
        cmbCategory.getSelectionModel().clearSelection();
        LBLitemcode.setText(addItemBo.genertateID());

    }

    public void CancelBtnSetOnAction(ActionEvent actionEvent) {
        clearField();
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
