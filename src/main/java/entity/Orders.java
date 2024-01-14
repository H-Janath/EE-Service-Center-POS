package entity;

import entity.Customer;
import entity.Inventory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Long orderId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "custid", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Inventory> inventoryList;

    // Getters and setters
}
