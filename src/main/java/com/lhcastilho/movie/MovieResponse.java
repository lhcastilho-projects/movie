package com.lhcastilho.movie;

import java.util.Set;

import com.lhcastilho.movie.producer.ProducerResponse;
import com.lhcastilho.movie.studio.StudioResponse;

public class MovieResponse {

    private Integer id;
    private String title;
    private Integer year;
    private Boolean winner;
    private Set<StudioResponse> studios;
    private Set<ProducerResponse> producers;
    private String error;

    public static MovieResponse build() {
        return new MovieResponse();
    }

    public Integer getId() {
        return id;
    }

    public MovieResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public MovieResponse setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Boolean getWinner() {
        return winner;
    }

    public MovieResponse setWinner(Boolean winner) {
        this.winner = winner;
        return this;
    }

    public Set<StudioResponse> getStudios() {
        return studios;
    }

    public MovieResponse setStudios(Set<StudioResponse> studios) {
        this.studios = studios;
        return this;
    }

    public Set<ProducerResponse> getProducers() {
        return producers;
    }

    public MovieResponse setProducers(Set<ProducerResponse> producers) {
        this.producers = producers;
        return this;
    }

    public String getError() {
        return error;
    }

    public MovieResponse setError(String error) {
        this.error = error;
        return this;
    }

}
