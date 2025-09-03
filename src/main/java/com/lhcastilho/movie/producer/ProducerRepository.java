package com.lhcastilho.movie.producer;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
public class ProducerRepository {

    @PersistenceContext
    private EntityManager em;

    public Producer create(Producer p) {
        em.persist(p);
        return p;
    }

    public Producer read(int id) {
        return em.find(Producer.class, id);
    }

    public Producer update(Producer p) {
        return em.merge(p);
    }

    public boolean delete(int id) {
        em.remove(read(id));
        return true;
    }

    public boolean clean() {
        int result = em.createNamedQuery("Producer.cleanup", Producer.class)
            .executeUpdate();
        return result > 0 ? true : false;
    }

    public Producer findByName(String name) {
        List<Producer> producerFound = em
            .createNamedQuery("Producer.findByName", Producer.class)
            .setParameter("name", name)
            .getResultList();

        if (producerFound.size() != 1) {
            return null;
        }

        return producerFound.get(0);
    }

    public boolean alreadyExists(String name) {
        List<Producer> producerFound = em
            .createNamedQuery("Producer.findByName", Producer.class)
            .setParameter("name", name)
            .getResultList();

        if (producerFound.size() == 0) {
            return false;
        }

        return true;
    }
}
