package com.lhcastilho.movie.producer;

import java.util.LinkedList;
import java.util.List;

import com.lhcastilho.movie.producer.intervals.ProducerIntervalDate;

public class ProducerIntervalResponse {

    private List<ProducerIntervalDate> min;
    private List<ProducerIntervalDate> max;

    public ProducerIntervalResponse() {
        this.min = new LinkedList<>();
        this.max = new LinkedList<>();
    }

    public List<ProducerIntervalDate> getMin() {
        return min;
    }

    public void setMin(List<ProducerIntervalDate> min) {
        this.min = min;
    }

    public void addMin(ProducerIntervalDate producer) {
        this.min.add(producer);
    }

    public List<ProducerIntervalDate> getMax() {
        return max;
    }

    public void setMax(List<ProducerIntervalDate> max) {
        this.max = max;
    }

    public void addMax(ProducerIntervalDate producer) {
        this.max.add(producer);
    }
}
