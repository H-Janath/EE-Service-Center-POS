package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryDto {
        private String customId;
        private String name;
        private String fault;
        private String status;
        private String category;
}
