package com.lhcastilho.movie.studio;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
public class StudioRepository {

    @PersistenceContext
    private EntityManager em;

    public Studio create(Studio p) {
        em.persist(p);
        return p;
    }

    public Studio read(int id) {
        return em.find(Studio.class, id);
    }

    public Studio update(Studio p) {
        return em.merge(p);
    }

    public boolean delete(int id) {
        em.remove(read(id));
        return true;
    }

    public boolean clean() {
        int result = em
            .createNamedQuery("Studio.cleanup", Studio.class)
            .executeUpdate();
        return result > 0 ? true : false;
    }

    public Studio findByName(String name) {
        List<Studio> studioFound = em
            .createNamedQuery("Studio.findByName", Studio.class)
            .setParameter("name", name)
            .getResultList();

        if (studioFound.size() != 1) {
            return null;
        }

        return studioFound.get(0);
    }

    public boolean alreadyExists(String name) {
        List<Studio> studioFound = em
            .createNamedQuery("Studio.findByName", Studio.class)
            .setParameter("name", name)
            .getResultList();

        if (studioFound.size() == 0) {
            return false;
        }

        return true;
    }

}
