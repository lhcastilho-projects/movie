package com.lhcastilho.movie.producer;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProducerMapper {

    public static ProducerResponse mapProducerError(String error) {
        return ProducerResponse
            .build()
            .setError(error);
    }

    public static ProducerResponse mapProducer(Producer producer) {
        return ProducerResponse
            .build()
            .setId(producer.getId())
            .setName(producer.getName());
    }

    public static Set<ProducerResponse> mapProducers(Set<Producer> producers) {
        Set<ProducerResponse> response = new HashSet<>();
        return producers
            .stream()
            .map( producer -> mapProducer(producer) )
            .collect(Collectors.toSet());
    }
}
