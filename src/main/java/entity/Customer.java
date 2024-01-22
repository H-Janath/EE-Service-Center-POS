package entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Entity
@Setter
@Getter
@NoArgsConstructor
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
    @Column(name = "contactNo")
    private String contactNo;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Orders> orders;



    public Customer(String customId, String name, String email, String address, String contactNo) {
        this.customId = customId;
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
        this.email = email;
    }

    public Customer(String customId, String name, String email, String address) {
        this.customId = customId;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
