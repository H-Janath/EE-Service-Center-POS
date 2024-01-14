package dao.impl;

import dao.utill.HibernateUtill;
import entity.Inventory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class InventoryDaoImpl {
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
