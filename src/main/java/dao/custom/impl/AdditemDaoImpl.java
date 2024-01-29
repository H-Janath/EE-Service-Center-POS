package dao.custom.impl;

import dao.custom.AdditemDao;
import dao.utill.HibernateUtill;
import entity.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdditemDaoImpl implements AdditemDao {
    public List<Item> getAll(){
        Session session = HibernateUtill.getSession();
        Query query = session.createQuery("FROM Item");
        List<Item> itemList = query.list();
        return itemList;
    }
    public boolean save(Item item){
        try(Session session = HibernateUtill.getSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
            session.clear();
            return true;
        }catch (HibernateException e) {
            e.printStackTrace(); // This is for demonstration purposes; use a proper logging mechanism in a real application
            return false; // Return null or throw a custom exception as needed
        }

    }

    @Override
    public boolean update(Item entity) {
        return false;
    }

    public Item getLastItem() {
        try (Session session = HibernateUtill.getSession()) {
            Transaction transaction = session.beginTransaction();

            // HQL query to find the item with the maximum ID
            String hql = "FROM Item ORDER BY id DESC";
            Query<Item> query = session.createQuery(hql, Item.class);
            query.setMaxResults(1);

            List<Item> items = query.list();
            transaction.commit();

            // Check if the list is not empty before returning the item
            return !items.isEmpty() ? new Item(
                    items.get(0).getItem_code(),
                    items.get(0).getName(),
                    items.get(0).getCategory()
            ) : null;
        } catch (HibernateException e) {
            // Handle exceptions appropriately (e.g., log the error)
            e.printStackTrace(); // This is for demonstration purposes; use a proper logging mechanism in a real application
            return null; // Return null or throw a custom exception as needed
        }
    }

    public boolean delete(String itemCode) {
        try(Session session = HibernateUtill.getSession();) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.find(Item.class,itemCode));
            transaction.commit();
            return true;
        }catch (HibernateException e){
            return false;
        }
    }
}
