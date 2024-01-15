package dao.impl;

import dao.utill.HibernateUtill;
import dto.CustomerDto;
import entity.Customer;
import entity.Item;
import entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityTransaction;
import java.util.List;

public class CustomerDaoImpl {
    public boolean save(CustomerDto customerDto) {
        Session session = HibernateUtill.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(new Customer(
                customerDto.getCustomId(),
                customerDto.getName(),
                customerDto.getEmail(),
                customerDto.getAddress(),
                customerDto.getContactNo()
        ));
        transaction.commit();
        session.clear();
        return true;

    }

    public Customer getLastCustomer() {
        try (Session session = HibernateUtill.getSession()) {
            Transaction transaction = session.beginTransaction();

            // HQL query to find the customer with the maximum custid
            String hql = "FROM Customer ORDER BY custid DESC";
            Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setMaxResults(1);

            List<Customer> customers = query.list();
            transaction.commit();

            // Check if the list is not empty before returning the customer
            return !customers.isEmpty() ? new Customer(
                    customers.get(0).getCustomId(),
                    customers.get(0).getName(),
                    customers.get(0).getEmail(),
                    customers.get(0).getAddress(),
                    customers.get(0).getContactNo()
            ) : null;
        } catch (HibernateException e) {
            // Handle exceptions appropriately (e.g., log the error)
            e.printStackTrace(); // This is for demonstration purposes; use a proper logging mechanism in a real application
            return null; // Return null or throw a custom exception as needed
        }
    }
}
