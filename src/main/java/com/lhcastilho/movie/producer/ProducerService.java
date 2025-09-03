package com.lhcastilho.movie.producer;

import static com.lhcastilho.movie.producer.ProducerMapper.mapProducer;
import static com.lhcastilho.movie.producer.ProducerMapper.mapProducerError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.lhcastilho.movie.Movie;
import com.lhcastilho.movie.MovieRepository;
import com.lhcastilho.movie.producer.intervals.Finder;
import com.lhcastilho.movie.producer.intervals.ProducerIntervalDate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProducerService {

    @Inject
    private MovieRepository movieRepo;

    @Inject
    private ProducerRepository producerRepo;

    public ProducerResponse createProducer(String name) {
        if (name == null || name.length() == 0) {
            String error = "O nome do produtor está vazio.";
            return mapProducerError(error);
        }

        if (producerRepo.alreadyExists(name)) {
            String error = "Um produtor com esse nome já existe.";
            return mapProducerError(error);
        }

        Producer newProducer = new Producer(name);
        Producer producerCreated = producerRepo.create(newProducer);
        if (producerCreated == null) {
            throw new RuntimeException("Erro ao criar o produtor.");
        }

        return mapProducer(producerCreated);
    }

    public ProducerResponse readProducer(int id) {
        if (id <= 0) {
            String error = "O id do produtor é um número inválido.";
            return mapProducerError(error);
        }

        Producer producer = producerRepo.read(id);
        if (producer == null) {
            return null;
        }

        return mapProducer(producer);
    }

    public ProducerResponse updateProducer(int id, String name) {
        if (name == null || name.length() == 0) {
            String error = "O nome do produtor está vazio.";
            return mapProducerError(error);
        }

        if (producerRepo.alreadyExists(name)) {
            String error = "Um produtor com esse nome já existe.";
            return mapProducerError(error);
        }

        Producer producerToUpdate = producerRepo.read(id);
        if (producerToUpdate == null) {
            String error = "Produtor não existe.";
            return mapProducerError(error);
        }

        producerToUpdate.setName(name);
        Producer producerUpdated = producerRepo.update(producerToUpdate);
        if (producerUpdated == null) {
            throw new RuntimeException("Erro ao atualizar o produtor.");
        }
        return mapProducer(producerUpdated);
    }

    public ProducerIntervalResponse awardsInterval(int beginY, int endY) {
        List<Movie> winnerMoviesInterval = movieRepo.winners(beginY, endY);

        Map<Integer, Producer> producers = new HashMap<>();
        Map<Integer, List<Integer>> awardYears = new HashMap<>();
        for(Movie winnerMovie: winnerMoviesInterval) {
            for (Producer producerWinner: winnerMovie.getProducers()) {
                if (producers.get(producerWinner.getId()) == null) {
                    producers.put(producerWinner.getId(), producerWinner);

                    List<Integer> years = new ArrayList<>();
                    years.add(winnerMovie.getYear());
                    awardYears.put(producerWinner.getId(), years);
                } 
                else {
                    awardYears.get(producerWinner.getId())
                        .add(winnerMovie.getYear());
                }
            }
        }

        TreeMap<Integer, List<ProducerIntervalDate>> minIntervalMap = new TreeMap<>();
        TreeMap<Integer, List<ProducerIntervalDate>> maxIntervalMap = new TreeMap<>();
        for(int id: awardYears.keySet()) {
            List<Integer> years = awardYears.get(id);
            if (years.size() > 1) {
                Finder find = new Finder();
                find.findIntervals(years, producers.get(id).getName());
                List<ProducerIntervalDate> minIntervals = find.getMinElements();
                if (minIntervalMap.containsKey(find.getMin())) {
                    List<ProducerIntervalDate> resultList = minIntervalMap.get(find.getMin());
                    resultList.addAll(minIntervals);
                }  else {
                    minIntervalMap.put(find.getMin(), minIntervals);
                }
                
                List<ProducerIntervalDate> maxIntervals = find.getMaxElements();
                if (maxIntervalMap.containsKey(find.getMax())) {
                    List<ProducerIntervalDate> resultList = maxIntervalMap.get(find.getMax());
                    resultList.addAll(maxIntervals);
                }  else {
                    maxIntervalMap.put(find.getMax(), maxIntervals);
                }
            }
        }

        ProducerIntervalResponse response = new ProducerIntervalResponse();
        Integer firstElement = minIntervalMap.firstKey();
        for(ProducerIntervalDate producer: minIntervalMap.get(firstElement)) {
            response.addMin(producer);
        }

        Integer lastElement = maxIntervalMap.lastKey();
        for(ProducerIntervalDate producer: maxIntervalMap.get(lastElement)) {
            response.addMax(producer);
        }

        return response;
    }

    public ProducerResponse deleteProducer(int id) {
        Producer toDelete = producerRepo.read(id);
        if (toDelete == null) {
            String error = "Produtor não existe.";
            return mapProducerError(error);
        }

        if (toDelete.getMovies().size() > 0) {
            String error = "O Produtor possui filmes associados a ele.";
            return mapProducerError(error);
        }

        if (producerRepo.delete(id)) {
            return mapProducer(toDelete);
        } else {
            throw new RuntimeException("Erro ao deletar o produtor.");
        }
    }
}
