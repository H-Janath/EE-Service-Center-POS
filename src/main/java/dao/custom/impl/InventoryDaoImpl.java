package dao.custom.impl;

import dao.custom.InventoryDao;
import dao.utill.HibernateUtill;
import entity.Inventory;
import entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class InventoryDaoImpl implements InventoryDao {
    public boolean saveList(List<Inventory> inventoryItems, String customId) {
        try (Session session = HibernateUtill.getSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Orders> criteriaQuery = criteriaBuilder.createQuery(Orders.class);
            Root<Orders> orderRoot = criteriaQuery.from(Orders.class);

            criteriaQuery.select(orderRoot)
                    .where(criteriaBuilder.equal(orderRoot.get("customId"), customId));

            Query<Orders> query = session.createQuery(criteriaQuery);
            Orders order = query.uniqueResult();

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
    public List<Inventory> getItem(int orderId){
        try (Session session = HibernateUtill.getSession()) {
            Transaction transaction = session.beginTransaction();

            // Assuming orderId is a variable, you should use a parameterized query to avoid SQL injection
            String hql = "FROM Inventory WHERE orderId = :orderId";

            // Assuming you have a Query object available
            Query<Inventory> query = session.createQuery(hql, Inventory.class);
            query.setParameter("orderId", orderId);

            // Retrieve the list of Inventory entities
            List<Inventory> inventoryList = query.list();
            return inventoryList;
            // Use 'inventoryList' as needed
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean save(Inventory entity) {
        return false;
    }

    @Override
    public boolean update(Inventory entity) {
        return false;
    }

    @Override
    public boolean delete(String entity) {
        return false;
    }

    @Override
    public List<Inventory> getAll() {
        return null;
    }


    public boolean updateStatus(Long inventoryid, String status) {
        try(Session session = HibernateUtill.getSession()){
            Transaction transaction = session.beginTransaction();
            Inventory inventory = session.get(Inventory.class,inventoryid);
            if(inventory!=null){
                System.out.println(inventory.getCustomId());
                inventory.setStatus(status);
                session.update(inventory);
                transaction.commit();
                return true;
            }else {
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
