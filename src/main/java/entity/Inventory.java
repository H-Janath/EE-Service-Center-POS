package entity;

import entity.Orders;

import javax.persistence.*;

@Entity
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
        this.customId = "I" + String.format("%04d", this.inventoryId);
    }
}
