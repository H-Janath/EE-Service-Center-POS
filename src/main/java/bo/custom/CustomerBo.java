package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;

import java.util.List;

public interface CustomerBo<T> extends SuperBo {
    public String genertateCustomerID();
    public boolean saveCustomer(T customerDto);
    public CustomerDto searchCustomer(String contactno);
    public List<CustomerDto> allCustomers();

}
