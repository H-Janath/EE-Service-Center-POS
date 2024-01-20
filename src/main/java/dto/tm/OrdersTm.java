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
    private JFXButton option;

}
