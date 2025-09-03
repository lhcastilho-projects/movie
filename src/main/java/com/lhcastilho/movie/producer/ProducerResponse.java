package com.lhcastilho.movie.producer;

public class ProducerResponse {

    private Integer id;
    private String name;
    private String error;

    public static ProducerResponse build() {
        return new ProducerResponse();
    }

    public Integer getId() {
        return id;
    }

    public ProducerResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProducerResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getError() {
        return error;
    }

    public ProducerResponse setError(String error) {
        this.error = error;
        return this;
    }

}
