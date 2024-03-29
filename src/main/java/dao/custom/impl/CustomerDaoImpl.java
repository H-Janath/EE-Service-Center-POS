package dao.custom.impl;
import dao.custom.CustomerDao;
import dao.utill.HibernateUtill;
import entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    public Customer search(String contactNum){
        try (Session session = HibernateUtill.getSession()) {
            Criteria criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.eq("contactNo", contactNum));
            return (Customer) ((Criteria) criteria).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace(); // Log the exception or handle it according to your needs
        }
        return null;
    }
    public boolean save(Customer customerDto) {
        try {
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
        }catch (HibernateException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Customer entity) {
        return false;
    }

    @Override
    public boolean delete(String entity) {
        return false;
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
    public List<Customer> getAll(){
        List<Customer> customerList = new ArrayList<>();
        try (Session session = HibernateUtill.getSession()) {
            // Replace 'Customer' with your actual entity class
            Query<Customer> query = session.createQuery("FROM Customer");
            customerList = query.list();

        }catch (HibernateException e){
            return null;
        }
        return customerList;
    }
}
