package com.lhcastilho.movie;

import static com.lhcastilho.movie.studio.StudioMapper.mapStudios;
import static com.lhcastilho.movie.producer.ProducerMapper.mapProducers;

import com.lhcastilho.movie.producer.Producer;
import com.lhcastilho.movie.studio.Studio;

import java.util.List;
import java.util.Set;

public class MovieMapper {

    public static MovieResponse mapMovieError(String error) {
        return MovieResponse
            .build()
            .setError(error);
    }

    public static Movie mapToMovie(String title, int year, boolean winner,
      Set<Studio> studios, Set<Producer> producers) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setYear(year);
        movie.setWinner(winner);
        movie.setStudios(studios);
        movie.setProducers(producers);
        return movie;
    }

    public static MovieResponse mapMovie(Movie movie) {
        return MovieResponse
            .build()
            .setId(movie.getId())
            .setTitle(movie.getTitle())
            .setYear(movie.getYear())
            .setWinner(movie.isWinner())
            .setStudios(mapStudios(movie.getStudios()))
            .setProducers(mapProducers(movie.getProducers()));
    }

    public static void mapMovie(Movie to, String title, Integer year, 
      Boolean winner, List<Studio> studios, List<Producer> producers) {
        if (title != null) {
            to.setTitle(title);
        }

        if (year != null) {
            to.setYear(year);
        }

        if (winner != null) {
            to.setWinner(winner);
        }

        if (studios != null && !studios.isEmpty()) {
            to.setStudios(null);
            studios.forEach(studio -> to.addStudio(studio));
        }
        
        if (producers != null && !producers.isEmpty()) {
            to.setProducers(null);
            producers.forEach(producer -> to.addProducer(producer));
        }
    }
}
