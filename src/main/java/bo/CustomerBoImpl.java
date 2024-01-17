package bo;
import dao.impl.CustomerDaoImpl;
import dto.CustomerDto;
import entity.Customer;
public class CustomerBoImpl {
    CustomerDaoImpl customerDao = new CustomerDaoImpl();
    public String genertateID() {
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
        return customerDao.save(customerDto);
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
}
