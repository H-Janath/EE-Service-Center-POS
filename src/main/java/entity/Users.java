package entity;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Data
public class Users {
    @Id
    private String email;
    private String password;
    private String role;

}