package dao.custom.impl;

import dao.custom.OrderDao;
import dao.utill.HibernateUtill;
import entity.Customer;
import entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    public boolean saveOrder(Orders order, String customId) {
        try (Session session = HibernateUtill.getSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> customerRoot = criteriaQuery.from(Customer.class);

            criteriaQuery.select(customerRoot)
                    .where(criteriaBuilder.equal(customerRoot.get("customId"), customId));

            Customer customer = session.createQuery(criteriaQuery).uniqueResult();

            if (customer != null) {
                order.setCustomer(customer);
                session.save(order);
                return true; // Indicate success
            } else {
                System.out.println("Customer with ID " + customId + " not found.");
                return false; // Indicate failure
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    public Orders getLastOrder(){
        try (Session session = HibernateUtill.getSession()) {
            Transaction transaction = session.beginTransaction();

            // HQL query to find the item with the maximum ID
            String hql = "FROM Orders ORDER BY id DESC";
            Query<Orders> query = session.createQuery(hql, Orders.class);
            query.setMaxResults(1);

            List<Orders> orders = query.list();
            transaction.commit();

            // Check if the list is not empty before returning the item
            return !orders.isEmpty() ? new Orders(
                    orders.get(0).getCustomId(),
                    orders.get(0).getDescription(),
                    orders.get(0).getStatus(),
                    orders.get(0).getAmount(),
                    orders.get(0).getDate()
            ) : null;
        } catch (HibernateException e) {
            e.printStackTrace(); // This is for demonstration purposes; use a proper logging mechanism in a real application
            return null;
        }
    }


    @Override
    public boolean save(Orders entity) {
        return false;
    }

    @Override
    public boolean update(Orders entity) {
        return false;
    }

    @Override
    public boolean delete(String entity) {
        return false;
    }

    public List<Orders> getAll(){
        try(Session session = HibernateUtill.getSession()){
            Query<Orders> query = session.createQuery("FROM Orders");
            List<Orders> ordersList = query.list();
            return ordersList;
        }catch (HibernateException e){
            return null;
        }
    }

    public List<Orders> findOrderList(String contactNo) {
        try (Session session = HibernateUtill.getSession()) {
            Query<Orders> query = session.createQuery("SELECT o FROM Orders o INNER JOIN o.customer c WHERE c.contactNo = :contactNo", Orders.class);
            query.setParameter("contactNo", contactNo);
            List<Orders> ordersList = query.getResultList();
            return ordersList;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
