package com.lhcastilho.movie.studio;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "studios")
@SequenceGenerator(
    name = "seq_studio",
    sequenceName = "seq_studio",
    initialValue = 1,
    allocationSize = 50)
@NamedQueries({
    @NamedQuery( 
        name = "Studio.findByName",
        query = "SELECT s FROM Studio s WHERE s.name = :name" ),
    @NamedQuery( 
        name = "Studio.cleanup",
        query = "DELETE FROM Studio s" )
})
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_studio")
    private int id;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "studios")
    private Set<Movie> movies;

    public Studio() { }

    public Studio(String name) { this.name = name; }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Set<Movie> getMovies() { return movies; }

    public void setMovies(Set<Movie> movies) { this.movies = movies; }

    @Override
    public String toString() {
        return "Studio [id=" + id + ", name=" + name + "]";
    }

}
