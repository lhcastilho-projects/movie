package com.lhcastilho.movie.loader;

public enum GlobalStrings {

    FILE_PATH("../../../../../../data/movielist.csv");

    private String value;

    GlobalStrings(String value) { this.value = value; }

    public String get() { return this.value; }
}
