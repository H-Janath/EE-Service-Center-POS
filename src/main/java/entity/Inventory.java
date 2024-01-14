package entity;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "Inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventoryid")
    private Long inventoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "category")
    private String category;
    @Column(name = "fault")
    private String fault;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Orders order;

    // Getters and setters
}
