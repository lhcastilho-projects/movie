package com.lhcastilho.movie;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
public class MovieRepository {

    @PersistenceContext
    private EntityManager em;

    public Movie create(Movie m) {
        em.persist(m);
        return m;
    }

    public Movie read(int id) {
        return em.find(Movie.class, id);
    }

    public Movie update(Movie p) {
        return em.merge(p);
    }

    public boolean delete(int id) {
        em.remove(read(id));
        return true;
    }

    public boolean clean() {
        int result = em.createNamedQuery("Movie.cleanup", Movie.class)
            .executeUpdate();
        return result > 0 ? true : false;
    }

    public boolean alreadyExists(String title) {
        List<Movie> movieFound = em
            .createNamedQuery("Movie.findByName", Movie.class)
            .setParameter("title", title)
            .getResultList();

        if (movieFound.size() == 0) {
            return false;
        }

        return true;
    }

    public List<Movie> winners(int beginY, int endY) {
        return em
            .createNamedQuery("Movie.winnersInInterval", Movie.class)
            .setParameter("winner", true)
            .setParameter("begin", beginY)
            .setParameter("end", endY)
            .getResultList();
    }
}
