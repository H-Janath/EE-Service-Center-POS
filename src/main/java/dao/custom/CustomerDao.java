package dao.custom;

import dao.CrudDao;
import entity.Customer;

public interface CustomerDao extends CrudDao<Customer> {
    public Customer search(String contactNum);
    public Customer getLastCustomer();
}
