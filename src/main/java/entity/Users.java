package entity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Data
public class Users {
    @Id
    private String email;
    private String password;
    private String role;
    private String otpCode;

    public Users(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}