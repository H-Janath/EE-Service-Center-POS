package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class InventoryTm2 extends RecursiveTreeObject<InventoryTm2> {
    private String id;
    private String Name;
    private String Category;
    private String Fault;
    private String Status;

}