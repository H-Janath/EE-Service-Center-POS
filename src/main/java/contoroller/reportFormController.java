package contoroller;
import bo.BoFactory;
import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.InventoryBoimpl;
import bo.custom.impl.OrderBoimpl;
import bo.util.BoType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import dto.OrderDto;
import dto.InventoryDto;
import dto.tm.OrdersTm;
import entity.Customer;
import entity.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class reportFormController {
    public JFXTextField txtId;
    public JFXTextField txtContact;
    public JFXButton idBtn;
    public JFXButton btnContact;
    CustomerBoImpl customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    InventoryBoimpl inventoryBoimpl = BoFactory.getInstance().getBo(BoType.INVENTORY);
    OrderBoimpl orderBoimpl = BoFactory.getInstance().getBo(BoType.ORDER);
    public Label lblTopic;

    public void backBtnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)lblTopic.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFormController.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.show();
    }


    public void CustomerReportBtnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/CustomersReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            // Use Hibernate session to get a connection
            List<CustomerDto> customerDtos = customerBo.allCustomers();
            List<Customer> customers = new ArrayList<>();
            for(CustomerDto customer: customerDtos){
                customers.add(
                        new Customer(
                                customer.getCustomId(),
                                customer.getName(),
                                customer.getAddress(),
                                customer.getEmail(),
                                customer.getContactNo()

                        )
                );
            }
            // Use the customerList as the data source for the report
                JRDataSource dataSource = new JRBeanCollectionDataSource(customerDtos);

                // Create an empty Map as there are no specific parameters for the report
                Map<String, Object> parameters = new HashMap<>();

                // Fill the report using the JasperFillManager with parameters
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                // Now you can use the filled JasperPrint object for further operations, such as exporting or viewing the report
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    public void OrderReportbyidBtnOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/OrderDetailsReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            int numericValue  = Integer.parseInt(txtId.getText().replaceAll("\\D*(\\d+).*", "$1"));
            // Use Hibernate session to get a connection
            List<InventoryDto> inventoryDtos = inventoryBoimpl.getOrderDetails(numericValue);
            List<Inventory> inventories = new ArrayList<>();
            for(InventoryDto inventory: inventoryDtos){
                inventories.add(
                        new Inventory(
                                inventory.getCustomId(),
                                inventory.getName(),
                                inventory.getFault(),
                                inventory.getStatus(),
                                inventory.getCategory()
                        )
                );
            }
            // Use the customerList as the data source for the report
            JRDataSource dataSource = new JRBeanCollectionDataSource(inventories);

            // Create an empty Map as there are no specific parameters for the report
            Map<String, Object> parameters = new HashMap<>();

            // Fill the report using the JasperFillManager with parameters
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Now you can use the filled JasperPrint object for further operations, such as exporting or viewing the report
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    public void OrderReportBycontactBtnOnAction(ActionEvent actionEvent) {
       try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/OrdersReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            String  phone  = txtContact.getText();
            // Use Hibernate session to get a connection
            List<OrderDto> orderDtos = orderBoimpl.findOrders(phone);
            List<OrdersTm> orderslist = new ArrayList<>();
            for(OrderDto orderDto: orderDtos){
                orderslist.add(
                        new OrdersTm(
                                orderDto.getCustomId(),
                                orderDto.getDate(),
                                orderDto.getStatus(),
                                orderDto.getAmount()
                        )
                );
            }
            // Use the customerList as the data source for the report
            JRDataSource dataSource = new JRBeanCollectionDataSource(orderDtos);

            // Create an empty Map as there are no specific parameters for the report
            Map<String, Object> parameters = new HashMap<>();

            // Fill the report using the JasperFillManager with parameters
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Now you can use the filled JasperPrint object for further operations, such as exporting or viewing the report
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
