package dao.impl;

import dao.utill.HibernateUtill;
import entity.Customer;
import entity.Inventory;

import entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class InventoryDaoImpl {
    public boolean save(List<Inventory> inventoryItems,String customId) {
        // Open a new Hibernate session
        try (Session session = HibernateUtill.getSession()) {
            // Begin a transaction
            Transaction transaction = session.beginTransaction();
            String hql = "FROM Orders c WHERE c.customId = :customId";
            Query<Orders> query = session.createQuery(hql, Orders.class);
            query.setParameter("customId", customId);

            Orders order= query.uniqueResult();
            try {
                // Associate the order with each inventory item
                for (Inventory inventoryItem : inventoryItems) {
                    inventoryItem.setOrder(order);
                    session.save(inventoryItem);
                }

                // Commit the transaction
                transaction.commit();

                // Return true indicating success
                return true;
            } catch (Exception e) {
                // Rollback the transaction in case of an exception
                transaction.rollback();
                e.printStackTrace();
                // Return false indicating failure
                return false;
            }
        }
    }

    public Inventory getLastItem() {
        try (Session session = HibernateUtill.getSession()) {
            Transaction transaction = session.beginTransaction();

            // HQL query to find the item with the maximum ID
            String hql = "FROM Inventory ORDER BY id DESC";
            Query<Inventory> query = session.createQuery(hql, Inventory.class);
            query.setMaxResults(1);

            List<Inventory> inventories = query.list();
            transaction.commit();

            // Check if the list is not empty before returning the item
            return !inventories.isEmpty() ? new Inventory(
                    inventories.get(0).getCustomId(),
                    inventories.get(0).getName(),
                    inventories.get(0).getFault(),
                    inventories.get(0).getStatus(),
                    inventories.get(0).getCategory()
            ) : null;
        } catch (HibernateException e) {
            // Handle exceptions appropriately (e.g., log the error)
            e.printStackTrace(); // This is for demonstration purposes; use a proper logging mechanism in a real application
            return null; // Return null or throw a custom exception as needed
        }
    }
}
