package entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventoryid")
    private Long inventoryId;

    @Column(name = "customId", unique = true, nullable = false)
    private String customId;

    @Column(name = "name")
    private String name;

    @Column(name = "fault")
    private String fault;

    @Column(name = "status")
    private String status;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Orders order;

    // Getters and setters


    public Inventory(Long inventoryId,String customId, String name, String fault, String status, String category) {
        this.inventoryId = inventoryId;
        this.customId = customId;
        this.name = name;
        this.fault = fault;
        this.status = status;
        this.category = category;
    }
    public Inventory(String customId, String name, String fault, String status, String category) {
        this.customId = customId;
        this.name = name;
        this.fault = fault;
        this.status = status;
        this.category = category;
    }

}
