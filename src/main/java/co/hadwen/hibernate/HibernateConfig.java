package co.hadwen.hibernate;

import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

@Getter
public class HibernateConfig {
    private final String configFileName;
    private final SessionFactory sessionFactory;

    HibernateConfig(@NonNull String configFileName) throws ExceptionInInitializerError {
        this.configFileName = configFileName;
        this.sessionFactory = buildSessionFactory(configFileName);
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    private static SessionFactory buildSessionFactory(@NonNull String configFileName) {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure(
                    new File(configFileName)).buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
