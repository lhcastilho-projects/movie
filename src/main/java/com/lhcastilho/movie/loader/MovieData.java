package com.lhcastilho.movie.loader;

import java.util.Set;

public class MovieData {
    private String title;
    private int year;
    private boolean winner;
    private Set<String> studios;
    private Set<String> producers;

    public MovieData() {}

    public MovieData(String title, int year, String winner, Set<String> studios, Set<String> producers) {
        this.year = year;
        this.title = title;
        this.winner = winner.equalsIgnoreCase("yes")  ? true : false;
        this.studios = studios;
        this.producers = producers;
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

    public Set<String> getStudios() {
        return studios;
    }

    public void setStudios(Set<String> studios) {
        this.studios = studios;
    }

    public Set<String> getProducers() {
        return producers;
    }

    public void setProducers(Set<String> producers) {
        this.producers = producers;
    }

    @Override
    public String toString() {
        return "MovieData [title=" + title + ", year=" + year + ", winner=" + winner + ", studios=" + studios
                + ", producers=" + producers + "]";
    }

}
