package entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
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

    @Column(name = "status")
    private String status;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Orders order;

    // Getters and setters

    @PrePersist
    public void generateCustomId() {
        // Assuming inventoryId is a numeric value
        this.customId = "IV" + String.format("%04d", this.inventoryId);
    }

    public Inventory(String customId, String name, String status, String category) {
        this.customId = customId;
        this.name = name;
        this.status = status;
        this.category = category;
    }
}
