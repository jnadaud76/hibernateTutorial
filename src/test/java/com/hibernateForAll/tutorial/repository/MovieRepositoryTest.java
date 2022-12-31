package com.hibernateForAll.tutorial.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hibernateForAll.tutorial.config.PersistenceConfig;
import com.hibernateForAll.tutorial.domain.Movie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")
@Sql({"/datas/datas-test.sql"})
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void save_casNominal() {
        Movie movie = new Movie();
        movie.setName("Inception");
        movieRepository.persist(movie);

    }

    @Test
    void merge_casSimule() {
        Movie movie = new Movie();
        movie.setName("Inception 2");
        movie.setId(-1L);
        Movie mergedMovie = movieRepository.merge(movie);
        assertThat(mergedMovie.getName()).as("le nom du film n'a pas été is à jour").isEqualTo("Inception 2");
    }

    @Test
    void find_casNominal() {
        Movie memento = movieRepository.find(-2L);
        assertThat(memento.getName()).as("mauvais film récupéré").isEqualTo("Memento");

    }

    @Test
    void getAll_caseNominal() {
        List<Movie> movies = movieRepository.getAll();
        assertThat(movies).as("l'ensemble des films n'a pas été récupéré").hasSize(2);
    }


    @Test
    void remove_casNominal() {
        movieRepository.remove(-2L);
        List<Movie> movies = movieRepository.getAll();
        assertThat(movies).as("le film n'a pas été supprimé").hasSize(1);
    }

    @Test
    void getReference_casNominal() {
        Movie movie = movieRepository.getReference(-2L);
        assertThat(movie.getId()).as("la référence n'a pas été correctement chargée").isEqualTo(-2L);
    }
}
