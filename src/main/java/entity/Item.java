package entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {
    @Id
    private String Item_code;
    private String name;
    private String Category;
}
