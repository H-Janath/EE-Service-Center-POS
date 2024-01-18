package dao.impl;
import dao.utill.HibernateUtill;
import dto.OrderDto;
import entity.Customer;
import entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl {
    public boolean save(OrderDto orderDto,String customId){
        try (Session session = HibernateUtill.getSession()) {
            String hql = "FROM Customer c WHERE c.customId = :customId";
            Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("customId", customId);

            Customer customer= query.uniqueResult();

            if (customer != null) {
                // Create a new Order and associate it with the customer
                Orders order = new Orders(
                        orderDto.getCustomId(),
                        orderDto.getDescription(),
                        orderDto.getStatus(),
                        orderDto.getAmount(),
                        orderDto.getDate()
                );
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
}
