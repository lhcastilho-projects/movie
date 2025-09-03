package com.lhcastilho.movie;

import java.util.List;

public class MovieData {

    private String title;
    private Integer year;
    private Boolean winner;
    private List<String> studios;
    private List<String> producers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    public List<String> getStudios() {
        return studios;
    }

    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    public boolean isEmpty() {
        return this.title == null &&
               this.year == null  &&
               this.winner == null &&
               this.studios == null &&
               this.producers == null;
    }

    @Override
    public String toString() {
        return "MovieData [title=" + title + ", year=" + year + ", winner=" + winner + ", studios=" + studios
                + ", producers=" + producers + "]";
    }

}
