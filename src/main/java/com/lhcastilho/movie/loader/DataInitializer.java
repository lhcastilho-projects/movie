package com.lhcastilho.movie.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lhcastilho.movie.Movie;
import com.lhcastilho.movie.MovieRepository;
import com.lhcastilho.movie.producer.Producer;
import com.lhcastilho.movie.producer.ProducerRepository;
import com.lhcastilho.movie.studio.Studio;
import com.lhcastilho.movie.studio.StudioRepository;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class DataInitializer {

    private static final Pattern LINE_PATTERN = Pattern
        .compile("^(\\d+);(.+);(.+);(.+);(.+)?$");

    @Inject
    private StudioRepository studiosRepository;
    
    @Inject
    private ProducerRepository producersRepository;

    @Inject
    private MovieRepository moviesRepository;

    @PostConstruct
    public void init() {
        // Check CSV file exists
        Path inputFilePath = Path.of(GlobalStrings.FILE_PATH.get());
        if (!Files.exists(inputFilePath)) {
            System.err.println("File was not found. Skip load data...");
            return;
        }

        try {

            // Clean up database
            cleanupDatabase();

            // Read file content
            List<String> lines = Files.readAllLines(inputFilePath);

            // Check header is OK
            String expectedHeader = "year;title;studios;producers;winner";
            if (!lines.remove(0).equalsIgnoreCase(expectedHeader)) {
                 System.err.println("CSV file has incompatible fields. Skip load data...");
                return;
            }

            // Load the data from CSV File...
            Set<String> studios = new HashSet<>();
            Set<String> producers = new HashSet<>();
            Set<MovieData> movies = new HashSet<>();
            for(String content: lines) {
                Matcher matcher = LINE_PATTERN.matcher(content);
                if (matcher.find()) {
                    // Load Studios
                    Set<String> movieStudios = 
                        mapElements(studios, matcher.group(3));
                    
                    // Load Producers
                    Set<String> movieProducers = 
                        mapElements(producers, matcher.group(4));

                    // Load Movies
                    mapMovies(movies, content,
                        movieStudios, movieProducers);
                }
            }

            // Save into database
            loadStudios(studios);
            loadProducers(producers);
            loadMovies(movies);

            // System.out.println("Movies loaded = " + movies.size());
        } catch (IOException e) {
            System.out.println("File cannot be loaded. " + e.getMessage());
        }
    }

    private void cleanupDatabase() {
        String movieCleanupResult = moviesRepository.clean()
            ? "Movies removed with success"
            : "No movie to remove";
        System.out.println(movieCleanupResult);
        
        String studioCleanupResult = studiosRepository.clean() 
            ? "Studios removed with success" 
            : "No studio to remove";
        System.out.println(studioCleanupResult);
        
        String producerleanupResult = producersRepository.clean() 
            ? "Producers removed with success" 
            : "No producer to remove";
        System.out.println(producerleanupResult);
    }

    private void loadStudios(Set<String> studios) {
        int loaded = 0;
        for(String studioName: studios) {
            Studio saved = studiosRepository.create(new Studio(studioName));
            if (saved != null) {
                loaded++;
            }
        }

        System.out.println("Studios loaded = " + loaded);
    }

    private void loadProducers(Set<String> producers) {
        int loaded = 0;
        for(String producerName: producers) {
            Producer saved = producersRepository.create(new Producer(producerName));
            if (saved != null) {
                loaded++;
            }
        }

        System.out.println("Producers loaded = " + loaded);
    }

    private void loadMovies(Set<MovieData> movies) {
        int loaded = 0;
        for(MovieData movie: movies) {
            Movie toSave = new Movie();
            toSave.setTitle(movie.getTitle());
            toSave.setYear(movie.getYear());
            toSave.setWinner(movie.isWinner());
            
            for(String studio: movie.getStudios()) {
                Studio studioSaved = studiosRepository.findByName(studio);
                if (studioSaved != null)
                    toSave.addStudio(studioSaved);
            }

            for(String producer: movie.getProducers()) {
                Producer producerSaved = producersRepository.findByName(producer);
                if (producerSaved != null)
                    toSave.addProducer(producerSaved);
            }

            Movie savedMovied = moviesRepository.create(toSave);
            if (savedMovied != null) {
                loaded++;
            }
        }

        System.out.println("Movies loaded = " + loaded);
    }

    private Set<String> mapElements(Set<String> elements, String content) {
        Set<String> partialElements = new HashSet<>();

        String[] elementsByAnd = content.split("\\hand\\h");
        for(String element: elementsByAnd) {
            if (element.contains(",")) {
                for(String subElement: element.split(",")) {
                    elements.add(subElement.trim());
                    partialElements.add(subElement.trim());
                }
            } else {
                elements.add(element.trim());
                partialElements.add(element.trim());
            }
        }

        return partialElements;
    }

    private void mapMovies(Set<MovieData> movies, String content, 
      Set<String> studios, Set<String> producers) {

        String[] fields = content.split(";");
        MovieData newMovie = new MovieData(
            fields[1],
            Integer.parseInt(fields[0]),
            fields.length == 5 ? fields[4] : "",
            studios,
            producers
        );

        movies.add(newMovie);
    }
}
