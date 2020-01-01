package co.hadwen.hibernate;

import co.hadwen.hibernate.exception.LazyInitializerException;
import lombok.NonNull;
import org.apache.commons.lang3.concurrent.LazyInitializer;

import java.io.File;
import java.net.URL;

public class HibernateConfigFactory {
    private static final String DEFAULT_CONFIG_FILENAME = "hibernate.cfg.xml";

    public HibernateConfig create(@NonNull File file) throws ExceptionInInitializerError {
        return new HibernateConfig(file);
    }

    public HibernateConfig create(
            @NonNull ClassLoader classLoader, @NonNull String fileName) throws ExceptionInInitializerError {
        URL url = classLoader.getResource(fileName);
        if(url == null) {
            throw new ExceptionInInitializerError();
        }
        return create(new File(url.getFile()));
    }

    public HibernateConfig create(@NonNull ClassLoader classLoader) throws ExceptionInInitializerError {
        return create(classLoader, DEFAULT_CONFIG_FILENAME);
    }

    public LazyInitializer<HibernateConfig> createLazy(@NonNull final ClassLoader classLoader) {
        return new LazyInitializer<HibernateConfig>() {
            @Override
            protected HibernateConfig initialize() throws LazyInitializerException {
                try {
                    return create(classLoader);
                } catch (ExceptionInInitializerError e) {
                    throw new LazyInitializerException();
                }
            }
        };
    }
}
