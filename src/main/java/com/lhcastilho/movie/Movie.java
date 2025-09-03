package com.lhcastilho.movie;

import java.util.HashSet;
import java.util.Set;

import com.lhcastilho.movie.producer.Producer;
import com.lhcastilho.movie.studio.Studio;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
@NamedQueries({
    @NamedQuery( 
        name = "Movie.findByName",
        query = "SELECT m FROM Movie m WHERE m.title = :title"),
    @NamedQuery( 
        name = "Movie.cleanup",
        query = "DELETE FROM Movie m"),
    @NamedQuery( 
        name = "Movie.winnersInInterval",
        query = "SELECT m FROM Movie m WHERE m.winner = :winner AND m.year >= :begin AND m.year <= :end ORDER BY m.year"),
})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String title;

    @Basic(optional = false)
    private int year;

    @Basic(optional = false)
    private boolean winner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "movie_producer",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private Set<Producer> producers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "movie_studio",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    private Set<Studio> studios;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public Set<Producer> getProducers() {
        return producers;
    }

    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }

    public void addProducer(Producer producer) {
        if (producers == null)
            this.producers = new HashSet<>();

        producers.add(producer);
    }

    public Set<Studio> getStudios() {
        return studios;
    }

    public void setStudios(Set<Studio> studios) {
        this.studios = studios;
    }

    public void addStudio(Studio studio) {
        if (studios == null)
            this.studios = new HashSet<>();

        studios.add(studio);
    }

    @Override
    public String toString() {
        return "Movie [id=" + id + ", title=" + title + ", year=" + year + ", winner=" + winner + ", producers="
                + producers + ", studios=" + studios + "]";
    }

}
