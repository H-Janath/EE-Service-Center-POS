package contoroller;
import Security.EmailService;
import bo.BoFactory;
import bo.custom.impl.*;
import bo.util.BoType;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.InventoryDto;
import dto.ItemDto;
import dto.OrderDto;
import dto.tm.InventoryTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFormContoller {
    public Label orderIdlbl;
    public JFXTextArea faultily;
    public TreeTableColumn colName;
    public TreeTableColumn colFault;
    public TreeTableColumn colCategory;
    public TreeTableColumn colStatus;
    public TreeTableColumn colOption;
    public JFXTreeTableView<InventoryTm> itemdetailstble;
    public TreeTableColumn colitemcode;
    public JFXTextField txtCustomername;
    public JFXTextField txtContactnum;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXComboBox cmbCategory;
    public JFXComboBox cmbItem;
    @FXML
    private AnchorPane menu_pane;

    String id;
    private ObservableList<String> tricalelist = FXCollections.observableArrayList();
    private ObservableList<String> tronicList = FXCollections.observableArrayList();
    private ObservableList<InventoryTm> inventoryTms = FXCollections.observableArrayList();
    private AddItemBoImpl addItemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderBoimpl orderBoimpl = BoFactory.getInstance().getBo(BoType.ORDER);
    private DashboardFormBoImpl dashboardFormBo = BoFactory.getInstance().getBo(BoType.DASHBOARD);
    private InventoryBoimpl inventoryBoimpl = BoFactory.getInstance().getBo(BoType.INVENTORY);
    private CustomerBoImpl customerBoImpl = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void clearFields(){
        txtCustomername.clear();
        txtContactnum.clear();
        txtEmail.clear();
        txtAddress.clear();
        faultily.setText("");
        itemdetailstble.getRoot().getChildren().clear();
    }
    public void initialize() {
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("Name"));
        colFault.setCellValueFactory(new TreeItemPropertyValueFactory<>("Fault"));
        colCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("Category"));
        colStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("Status"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        colitemcode.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        orderIdlbl.setText(orderBoimpl.genertatOrderID());
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
        });
        itemdetailstble.setOnMouseClicked(event -> {
            cmbCategory.getSelectionModel().clearSelection();
            cmbItem.getSelectionModel().clearSelection();
            if(event.getClickCount()==1&&(!itemdetailstble.getSelectionModel().isEmpty())){
                TreeItem<InventoryTm> inventoryTmTreeItem = itemdetailstble.getSelectionModel().getSelectedItem();
                faultily.setText(inventoryTmTreeItem.getValue().getFault());
            }
        });

    }
    public void searchBtnSetAction(ActionEvent actionEvent) {
            String contact = txtContactnum.getText();
            CustomerDto customerDto = customerBoImpl.searchCustomer(contact);
            if(customerDto!=null) {
                txtCustomername.setText(customerDto.getName());
                txtEmail.setText(customerDto.getEmail());
                txtAddress.setText(customerDto.getAddress());
            }else{
                txtCustomername.clear();
                txtEmail.clear();
                txtAddress.clear();
                new Alert(Alert.AlertType.ERROR,"This customer not registered").show();
            }
    }
    private void showValidationError(String message) {
        Tooltip tooltip = new Tooltip(message);
        txtCustomername.setTooltip(tooltip);
        txtCustomername.setStyle("-fx-border-color: red;");
    }
    private void clearValidationError() {
        txtCustomername.setTooltip(null);
    }
    public void placeBtnSetAction(ActionEvent actionEvent) {
        if(inventoryTms.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"pleace add item").show();
        }else {
            boolean savedCustomer=false;
            CustomerDto customer = customerBoImpl.searchCustomer(txtContactnum.getText());
            boolean isavailabel=customer!=null?true:false;
            if(!isavailabel) {
                id = customerBoImpl.genertateCustomerID();
                savedCustomer = customerBoImpl.saveCustomer(
                        new CustomerDto(
                                id,
                                txtCustomername.getText(),
                                txtEmail.getText(),
                                txtAddress.getText(),
                                txtContactnum.getText()
                        )
                );
            }else{
                id=customer.getCustomId();
            }
        if(savedCustomer||isavailabel){
            OrderDto orderDto = new OrderDto(
                    orderIdlbl.getText(),
                    null,
                    "PROCESSING",
                    0.0,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")).toString()
            );
            boolean savedOrder=orderBoimpl.saveOrder(orderDto, id);
            if(savedOrder) {
                List<InventoryDto> dtoList = new ArrayList<>();
                for (InventoryTm inventoryTm: inventoryTms){
                    dtoList.add(
                            new InventoryDto(
                                    inventoryTm.getId(),
                                    inventoryTm.getName(),
                                    inventoryTm.getFault(),
                                    inventoryTm.getStatus(),
                                    inventoryTm.getCategory()
                            )
                    );
                }
                inventoryBoimpl.saveInventoryItems(dtoList,orderDto.getCustomId());
                orderIdlbl.setText(orderBoimpl.genertatOrderID());
                generateAndSendBillList(dtoList);
                new Alert(Alert.AlertType.INFORMATION,"Bill generated and sent successfully to Email").show();
                clearFields();

            }
        }
        }
    }
    public void ORDERS_btnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)menu_pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrdersForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Orders Form");
        stage.show();
    }
    private void generateAndSendBillList(List<InventoryDto> inventories) {
        try {
            // Load JasperReport template
            String reportFilePath = "src/main/resources/report/OrderDetailsReport.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFilePath);

            // Use the customerList as the data source for the report
            JRDataSource dataSource = new JRBeanCollectionDataSource(inventories);

            // Create an empty Map as there are no specific parameters for the report
            Map<String, Object> parameters = new HashMap<>();

            // Fill the report using the JasperFillManager with parameters
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            // Specify the file path where you want to save the generated bill
            String saveFilePath = "src/main/resources/bills/bill.pdf";

            // Generate and save the bill as a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, saveFilePath);

            // Send the generated bill to the customer's email
            String customerEmail = "janathhma@gmail.com"; // Replace with actual customer email
            EmailService.sendEmailWithAttachment(customerEmail, "Your Order Bill", "Please find attached your order bill.", saveFilePath);

            System.out.println("Bill generated and sent successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void CUSTOMERS_btoSetONAction(ActionEvent actionEvent) {
    }

    public void REPORT_btnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)menu_pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ReportForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Report Form");
        stage.show();
    }

    public void USERS_btnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)menu_pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddUserAccountForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Add Users");
        stage.show();
    }

    public void INVENTORY_btnSetOnAction(ActionEvent actionEvent) {
    }



    public void addBtnSetAction(ActionEvent actionEvent) {
        if (isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter customer details").show();
        }else if(cmbItem.getSelectionModel().getSelectedItem()==null){
            new Alert(Alert.AlertType.ERROR, "Please select item").show();
        }else {
            if (inventoryTms.isEmpty()) {
                id = inventoryBoimpl.genertateInventoryID();
            } else {
                int lastIndex = inventoryTms.size() - 1;
                InventoryTm lastObject = inventoryTms.get(lastIndex);
                id = lastObject.getId();
                int num = Integer.parseInt(id.split("IV")[1]);
                num++;
                id = String.format("IV%04d", num);
            }
            JFXButton btn = new JFXButton("Delete");
            InventoryTm inventoryTm = new InventoryTm(
                    cmbItem.getSelectionModel().getSelectedItem().toString(),
                    faultily.getText(),
                    cmbCategory.getSelectionModel().getSelectedItem().toString(),
                    "Pending",
                    id,
                    btn
            );
            btn.setOnAction(ActionEvent -> {
                inventoryTms.remove(inventoryTm);
                itemdetailstble.refresh();
            });
            inventoryTms.add(inventoryTm);
            RecursiveTreeItem<InventoryTm> treeItem = new RecursiveTreeItem<>(inventoryTms, RecursiveTreeObject::getChildren);
            itemdetailstble.setRoot(treeItem);
            itemdetailstble.setShowRoot(false);
        }
    }

    public void updateBtnSetAction(ActionEvent actionEvent) {
    }

    public void cancelBtnSetAction(ActionEvent actionEvent) {
        clear();
    }


    public void additm_btnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)menu_pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AddItemForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Add item Form");
        stage.show();
    }
    private void clear(){
        cmbCategory.getSelectionModel().clearSelection();
        cmbItem.getSelectionModel().clearSelection();
        faultily.clear();
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
    private boolean isEmpty(){
         if(txtCustomername.getText().isEmpty()|| txtContactnum.getText().isEmpty()||txtEmail.getText().isEmpty()||txtAddress.getText().isEmpty()){
             return true;
         }
         return false;
    }


    public void CASHIER_btnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)menu_pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CashierForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Orders Form");
        stage.show();
    }
}
