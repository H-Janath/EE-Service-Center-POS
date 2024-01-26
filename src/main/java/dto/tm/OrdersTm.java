package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrdersTm extends RecursiveTreeObject<OrdersTm> {
    private String orderId;
    private String customerID;
    private String date;
    private String status;
    private double amount;
    private JFXButton option;

    public OrdersTm(String orderId, String customerID, String date, String status, JFXButton option) {
        this.orderId = orderId;
        this.customerID = customerID;
        this.date = date;
        this.status = status;
        this.option = option;
    }

    public OrdersTm(String customerID, String date, String status,double amount) {
        this.customerID = customerID;
        this.date = date;
        this.status = status;
        this.amount =amount;
    }
}
