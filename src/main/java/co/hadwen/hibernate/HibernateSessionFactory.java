package co.hadwen.hibernate;

import lombok.Getter;
import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.annotation.PreDestroy;

@Getter
public class HibernateSessionFactory {
    private static final String DEFAULT_CONFIG_FILENAME = "hibernate.cfg.xml";
    private final SessionFactory sessionFactory;

    public HibernateSessionFactory(@NonNull Configuration configuration) {
        this.sessionFactory = buildSessionFactory(configuration);
    }

    public HibernateSession create() {
        return new HibernateSession(sessionFactory);
    }

    @PreDestroy
    public void shutDown() {
        sessionFactory.close();
    }

    private static SessionFactory buildSessionFactory(Configuration configuration) {
        try {
            return configuration.buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
