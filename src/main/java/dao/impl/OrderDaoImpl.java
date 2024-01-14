package dao.impl;

import dao.utill.HibernateUtill;
import entity.Item;
import entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl {
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
            // Handle exceptions appropriately (e.g., log the error)
            e.printStackTrace(); // This is for demonstration purposes; use a proper logging mechanism in a real application
            return null; // Return null or throw a custom exception as needed
        }
    }
}
