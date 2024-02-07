package bo.custom.impl;
import bo.custom.CustomerBo;
import dao.custom.CustomerDao;
import dao.custom.impl.CustomerDaoImpl;
import dto.CustomerDto;
import entity.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo<CustomerDto> {
    private CustomerDao customerDao = new CustomerDaoImpl();
    public String genertateCustomerID() {
        Customer dto = customerDao.getLastCustomer();
        if (dto != null) {
            String id = dto.getCustomId();
            if (id != null) {
                int num = Integer.parseInt(id.split("C")[1]);
                num++;
                return String.format("C%04d", num);
            }
        }
        return "C0001";
    }

    public boolean saveCustomer(CustomerDto customerDto){
        return customerDao.save(
                new Customer(
                        customerDto.getCustomId(),
                        customerDto.getName(),
                        customerDto.getAddress(),
                        customerDto.getContactNo(),
                        customerDto.getEmail()
                )
        );
    }
    public CustomerDto searchCustomer(String contactno){
        Customer customer =customerDao.search(contactno);
        if(customer!=null){
        return new CustomerDto(
                customer.getCustomId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getContactNo()
        );}
        return null;
    }
    public List<CustomerDto> allCustomers() {
        List<Customer> allcustomers = customerDao.getAll();
        if (allcustomers != null) {
            List<CustomerDto> dtoList = new ArrayList<>();
            for (Customer customer : allcustomers) {
                dtoList.add(
                        new CustomerDto(
                                customer.getCustomId(),
                                customer.getName(),
                                customer.getEmail(),
                                customer.getAddress(),
                                customer.getContactNo()

                        )
                );
            }
            return dtoList;
        }
        return null;
    }
}
