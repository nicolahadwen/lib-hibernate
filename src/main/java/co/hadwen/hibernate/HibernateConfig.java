package co.hadwen.hibernate;

import lombok.Getter;
import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

@Getter
public class HibernateConfig {
    private final String configFileName;
    private final SessionFactory sessionFactory;

    private HibernateConfig() throws ExceptionInInitializerError {
        this.configFileName = "hibernate.cfg.xml";
        this.sessionFactory = buildSessionFactory();
    }

    private HibernateConfig(@NonNull String configFileName) throws ExceptionInInitializerError {
        this.configFileName = configFileName;
        this.sessionFactory = buildSessionFactory();
    }

    public void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public static HibernateConfig create() {
        return new HibernateConfig();
    }

    public static HibernateConfig create(@NonNull String lombok) {
        return new HibernateConfig(lombok);
    }


    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure(
                    new File("hibernate.cfg.xml")).buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
