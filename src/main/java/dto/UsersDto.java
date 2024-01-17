package dto;
import lombok.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private String email;
    private String password;
    private String role;
}