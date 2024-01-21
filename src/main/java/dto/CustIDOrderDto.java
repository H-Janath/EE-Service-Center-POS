package dto;

import entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustIDOrderDto {
    private String custID;
    private String customId;
    private String description;
    private String status;
    private double amount;
    private String date;
    private List<Inventory> inventoryList;
}
