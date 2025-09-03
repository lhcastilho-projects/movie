package com.lhcastilho.movie.studio;

public class StudioResponse {

    private Integer id;
    private String name;
    private String error;

    public static StudioResponse build() {
        return new StudioResponse();
    }

    public Integer getId() {
        return id;
    }

    public StudioResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudioResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getError() {
        return error;
    }

    public StudioResponse setError(String error) {
        this.error = error;
        return this;
    }

}
