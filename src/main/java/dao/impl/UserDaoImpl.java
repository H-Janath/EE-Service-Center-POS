package dao.impl;

import dao.utill.HibernateUtill;
import entity.Users;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserDaoImpl {
    public Users search(String email){
        try (Session session = HibernateUtill.getSession()) {
            Criteria criteria = session.createCriteria(Users.class);
            criteria.add(Restrictions.eq("email", email));
            return (Users) ((Criteria) criteria).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace(); // Log the exception or handle it according to your needs
        }
        return null;
    }
    public boolean save(Users users) {
        try (Session session = HibernateUtill.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                session.save(users);
                transaction.commit();
                return true;
            } catch (Exception e) {
                // Handle the exception, possibly log it
                if (transaction != null) {
                    transaction.rollback();
                }
                return false;
            }
        }
    }

    public boolean update(String email, String password) {
        try (Session session = HibernateUtill.getSession()) {
            Transaction transaction = session.beginTransaction();

            // Assuming Users class has a constructor that accepts email as a parameter
            Users user = session.get(Users.class, email);

            if (user != null) {
                // Update the user's information
                user.setPassword(password);

                // Save the changes to the database
                session.update(user);

                // Commit the transaction
                transaction.commit();

                return true;
            } else {
                // User not found
                return false;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., log or throw)
            e.printStackTrace();
            return false;
        }
    }

}
