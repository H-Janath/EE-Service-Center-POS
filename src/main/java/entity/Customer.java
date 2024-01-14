package entity;

import entity.Orders;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custid")
    private Long custId;

    @Column(name = "customId", unique = true, nullable = false)
    private String customId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_no")
    private String contactNo;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Orders> orders;

    // Getters and setters

    @PrePersist
    public void generateCustomId() {
        // Assuming custId is a numeric value
        this.customId = "C" + String.format("%04d", this.custId);
    }
}
