package com.lhcastilho.movie;

import static com.lhcastilho.movie.MovieMapper.mapToMovie;
import static com.lhcastilho.movie.MovieMapper.mapMovie;
import static com.lhcastilho.movie.MovieMapper.mapMovieError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lhcastilho.movie.producer.Producer;
import com.lhcastilho.movie.producer.ProducerRepository;
import com.lhcastilho.movie.studio.Studio;
import com.lhcastilho.movie.studio.StudioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MovieService {

    @Inject
    private MovieRepository movieRepo;

    @Inject
    private StudioRepository studioRepo;

    @Inject
    private ProducerRepository producerRepo;

    public MovieResponse createMovie(String title, Integer year, Boolean winner,
      List<String> studios, List<String> producers) {

        if (title == null || title.length() == 0) {
            String error = "O título do filme está vazio.";
            return mapMovieError(error);
        }

        if (year == null || year <= 0) {
            String error = "O ano do filme está vazio ou inválido.";
            return mapMovieError(error);
        }

        if (winner == null) {
            winner = false;
        }

        if (studios == null || studios.isEmpty()) {
            String error = "O filme não possui nenhum estúdio.";
            return mapMovieError(error);
        }

        if (producers == null || producers.isEmpty()) {
            String error = "O filme não possui nenhum produtor.";
            return mapMovieError(error);
        }

        if (movieRepo.alreadyExists(title)) {
            String error = "Um filme com esse nome já existe.";
            return mapMovieError(error);
        }

        Set<Studio> studioSet = new HashSet<>();
        for (String studio: studios) {
            Studio studioFound = studioRepo.findByName(studio);
            if (studioFound == null) {
                String error = "O estúdio " + studio + " não existe.";
                return mapMovieError(error);
            }

            studioSet.add(studioFound);
        }

        Set<Producer> producerSet = new HashSet<>();
        for (String producer: producers) {
            Producer producerFound = producerRepo.findByName(producer);
            if (producerFound == null) {
                String error = "O produtor " + producer + " não existe.";
                return mapMovieError(error);
            }

            producerSet.add(producerFound);
        }

        Movie newMovie = mapToMovie(title, year, winner, studioSet, producerSet);
        Movie movieCreated = movieRepo.create(newMovie);
        if (movieCreated == null) {
            throw new RuntimeException("Erro ao criar o filme.");
        }

        return mapMovie(movieCreated);
    }

    public MovieResponse readMovie(int id) {
        if (id <= 0) {
            String error = "O id do filme é um número inválido.";
            return mapMovieError(error);
        }

        Movie movie = movieRepo.read(id);
        if (movie == null) {
            return null;
        }

        return mapMovie(movie);
    }

    public MovieResponse updateMovie(int id, MovieData dataToUpdate) {
        if (dataToUpdate == null || dataToUpdate.isEmpty()) {
            throw new RuntimeException("Erro ao atualizar o filme. Não há dados para atualizar.");
        }

        Movie movieToUpdate = movieRepo.read(id);
        if (movieToUpdate == null) {
            String error = "Filme não existe e não será atualizado.";
            return mapMovieError(error);
        }

        String titleToUpdate = dataToUpdate.getTitle();
        if (movieToUpdate.getTitle().equals(titleToUpdate)) {
            titleToUpdate = null;
        } else {
            if (titleToUpdate != null && movieRepo.alreadyExists(titleToUpdate)) {
                String error = "Um filme com esse título já existe.";
                return mapMovieError(error);
            }
        }

        Integer yearToUpdate = dataToUpdate.getYear();
        if (yearToUpdate != null && movieToUpdate.getYear() == yearToUpdate) {
            yearToUpdate = null;
        } else {
            if (yearToUpdate != null && yearToUpdate <= 0) {
                String error = "O filme possui um ano inválido.";
                return mapMovieError(error);
            }
        }

        Boolean winnerToUpdate = dataToUpdate.getWinner();
        if (winnerToUpdate != null && movieToUpdate.isWinner() == winnerToUpdate) {
            winnerToUpdate = null;
        }

        List<Studio> studioCache = new ArrayList<>();
        if (dataToUpdate.getStudios() != null) {
            for(String studioName: dataToUpdate.getStudios()) {
                Studio studio = studioRepo.findByName(studioName);
                if (studio == null) {
                    String error = "O estúdio " + studioName + " não existe.";
                    return mapMovieError(error);
                }

                studioCache.add(studio);
            }
        }

        List<Producer> producerCache = new ArrayList<>();
        if (dataToUpdate.getProducers() != null) {
            for(String producerName: dataToUpdate.getProducers()) {
                Producer producer = producerRepo.findByName(producerName);
                if (producer == null) {
                    String error = "O produtor " + producerName + " não existe.";
                    return mapMovieError(error);
                }

                producerCache.add(producer);
            }
        }

        mapMovie(movieToUpdate, titleToUpdate, yearToUpdate,
            winnerToUpdate, studioCache, producerCache);
        Movie movieUpdated = movieRepo.update(movieToUpdate);
        if (movieUpdated == null) {
            throw new RuntimeException("Erro ao atualizar o filme.");
        }

        return mapMovie(movieUpdated);
    }

    public MovieResponse deleteMovie(int id) {
        Movie toDelete = movieRepo.read(id);
        if (toDelete == null) {
            String error = "Filme não existe.";
            return mapMovieError(error);
        }

        if (movieRepo.delete(id)) {
            return mapMovie(toDelete);
        } else {
            throw new RuntimeException("Erro ao deletar o filme.");
        }
    }

}
