package co.hadwen.hibernate;

import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

@Getter
public class HibernateConfig {
    private final File configFile;
    private final SessionFactory sessionFactory;

    HibernateConfig(@NonNull File configFile) throws ExceptionInInitializerError {
        this.configFile = configFile;
        this.sessionFactory = buildSessionFactory(configFile);
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    private static SessionFactory buildSessionFactory(@NonNull File configFile) {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure(configFile).buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
