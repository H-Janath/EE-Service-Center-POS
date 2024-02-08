package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PartsTm extends RecursiveTreeObject<PartsTm> {
    private String name;
    private Double price;
}
