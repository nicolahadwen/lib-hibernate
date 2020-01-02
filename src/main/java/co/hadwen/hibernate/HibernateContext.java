package co.hadwen.hibernate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@AllArgsConstructor
@Getter
public class HibernateContext {
    private final SessionFactory sessionFactory;

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
