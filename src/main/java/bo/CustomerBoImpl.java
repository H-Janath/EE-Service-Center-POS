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
        // If dto is null or id is null, or parsing fails, return a default value
        return "C0001";
    }

    public boolean saveCustomer(CustomerDto customerDto){
        return customerDao.save(customerDto);
    }
}
