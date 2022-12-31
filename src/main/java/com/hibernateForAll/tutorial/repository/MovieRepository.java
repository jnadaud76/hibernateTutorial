package com.hibernateForAll.tutorial.repository;

import com.hibernateForAll.tutorial.domain.Movie;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Repository
public class MovieRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void persist(Movie movie) {
        entityManager.persist(movie);
        LOGGER.trace("entityManager.contains() : "+ entityManager.contains(movie));
        entityManager.detach(movie);
        LOGGER.trace("entityManager.contains() : "+ entityManager.contains(movie));

    }

    @Transactional
    public Movie merge(Movie movie) {
        return entityManager.merge(movie);
    }

    public Movie find(Long id) {
        Movie result = entityManager.find(Movie.class, id);
        LOGGER.trace("entityManager.contains() : "+ entityManager.contains(result));
        return result;
    }

    public List<Movie> getAll() {
        return entityManager.createQuery("from Movie", Movie.class).getResultList();
    }

    @Transactional
    public void remove(Long l) {
        Movie movie = entityManager.find(Movie.class, l);
        entityManager.remove(movie);
    }

    @Transactional
    public Movie getReference(Long l) {
        Movie result =  entityManager.getReference(Movie.class, l);
        LOGGER.trace("movie name : "+ result.getName());
        return result;
    }
}
