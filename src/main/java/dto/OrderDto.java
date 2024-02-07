package dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDto {
    private String customId;
    private String description;
    private String status;
    private double amount;
    private String date;
}
