package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class InventoryTm extends RecursiveTreeObject<InventoryTm> {
    private String Name;
    private String Fault;
    private String Category;
    private String Status;
    private String id;
    JFXButton btn;
}