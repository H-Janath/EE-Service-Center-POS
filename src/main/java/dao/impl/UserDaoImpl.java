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

}
