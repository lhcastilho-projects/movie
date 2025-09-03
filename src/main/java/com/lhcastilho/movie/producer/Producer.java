package com.lhcastilho.movie.producer;

import java.util.Set;

import com.lhcastilho.movie.Movie;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "producers")
@NamedQueries({
    @NamedQuery( 
        name = "Producer.findByName",
        query = "SELECT p FROM Producer p WHERE p.name = :name" ),
    @NamedQuery( 
        name = "Producer.cleanup",
        query = "DELETE FROM Producer p" ),
})
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "producers")
    private Set<Movie> movies;

    public Producer() { }

    public Producer(String name) { this.name = name; }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Set<Movie> getMovies() { return movies; }

    public void setMovies(Set<Movie> movies) { this.movies = movies; }

    @Override
    public String toString() {
        return "Producer [id=" + id + ", name=" + name + "]";
    }

}
