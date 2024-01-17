package dao.impl;
import dao.utill.HibernateUtill;
import entity.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserAccountDoImpl {
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

}
