package co.hadwen.hibernate;

import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateContextFactory {
    private static final String DEFAULT_CONFIG_FILENAME = "hibernate.cfg.xml";

    public HibernateContext create(@NonNull File file) throws ExceptionInInitializerError {
        return new HibernateContext(buildSessionFactory(file));
    }

    public HibernateContext create(@NonNull String fileName) throws ExceptionInInitializerError {
        return new HibernateContext(buildSessionFactory(fileName));
    }

    public HibernateContext create() throws ExceptionInInitializerError {
        return new HibernateContext(buildSessionFactory(DEFAULT_CONFIG_FILENAME));
    }

    public HibernateContext create(@NonNull Configuration configuration) throws ExceptionInInitializerError {
        return new HibernateContext(buildSessionFactory(configuration));
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

    private static SessionFactory buildSessionFactory(@NonNull String configFileName) {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure(configFileName).buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionFactory(Configuration configuration) {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return configuration.configure(DEFAULT_CONFIG_FILENAME).buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
