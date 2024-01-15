package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class CustomerDto {
    private String customId;
    private String name;
    private String email;
    private String address;
    private String contactNo;
}
