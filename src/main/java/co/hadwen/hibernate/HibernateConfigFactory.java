package co.hadwen.hibernate;

import co.hadwen.hibernate.exception.LazyInitializerException;
import lombok.NonNull;
import org.apache.commons.lang3.concurrent.LazyInitializer;

public class HibernateConfigFactory {
    private static final String DEFAULT_CONFIG_FILENAME = "hibernate.cfg.xml";

    public HibernateConfig create() throws ExceptionInInitializerError {
        return create(DEFAULT_CONFIG_FILENAME);
    }

    public HibernateConfig create(@NonNull String configFileName) throws ExceptionInInitializerError {
        return new HibernateConfig(configFileName);
    }

    public LazyInitializer<HibernateConfig> createLazy() {
        return createLazy(DEFAULT_CONFIG_FILENAME);
    }

    public LazyInitializer<HibernateConfig> createLazy(@NonNull final String configFileName) {
        return new LazyInitializer<HibernateConfig>() {
            @Override
            protected HibernateConfig initialize() throws LazyInitializerException {
                try {
                    return create(configFileName);
                } catch (ExceptionInInitializerError e) {
                    throw new LazyInitializerException();
                }
            }
        };
    }
}
