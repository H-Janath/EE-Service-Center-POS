package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Parts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double cost;
    @ManyToOne
    @JoinColumn(name = "inventoryid", nullable = false)
    public Inventory inventory;

    public Parts(String name, Double cost){
        this.name = name;
        this.cost = cost;
    }
}
