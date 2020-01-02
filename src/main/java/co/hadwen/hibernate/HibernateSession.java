package co.hadwen.hibernate;

import lombok.Getter;
import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.PreDestroy;

@Getter
public class HibernateSession {
    private final Session session;

    HibernateSession(@NonNull SessionFactory sessionFactory) {
        this.session = sessionFactory.openSession();
    }

    @PreDestroy
    void shutDown() {
        session.close();
    }
}
