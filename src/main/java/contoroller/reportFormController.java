package contoroller;

import bo.CustomerBoImpl;
import dto.CustomerDto;
import entity.Customer;
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
    CustomerBoImpl customerBo = new CustomerBoImpl();

    public Label lblTopic;

    public void backBtnSetOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)lblTopic.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFormController.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.show();
    }

    public void OrderReportBtnOnAction(ActionEvent actionEvent) {
    }



    public void CustomerReportBtnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/CustomersReports.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            // Use Hibernate session to get a connection
            List<CustomerDto> customerDtos = customerBo.allCustomers();
            List<Customer> customers = new ArrayList<>();
            for(CustomerDto customer: customerDtos){
                customers.add(
                        new Customer(
                                customer.getCustomId(),
                                customer.getName(),
                                customer.getEmail(),
                                customer.getAddress()
                        )
                );
            }
            // Use the customerList as the data source for the report
                JRDataSource dataSource = new JRBeanCollectionDataSource(customers);

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


    public void SalesReportBtnOnAction(ActionEvent actionEvent) {
    }
}
