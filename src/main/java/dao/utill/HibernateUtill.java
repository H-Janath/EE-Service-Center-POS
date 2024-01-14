package dao.utill;
import entity.Customer;
import entity.Inventory;
import entity.Item;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtill {
    private static SessionFactory sessionFactory = createSessionFactory();
    private static SessionFactory createSessionFactory(){
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Orders.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadata.getSessionFactoryBuilder()
                .build();
    }
    public static Session getSession(){
        return sessionFactory.openSession();
    }
}
