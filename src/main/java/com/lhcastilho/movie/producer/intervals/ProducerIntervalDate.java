package com.lhcastilho.movie.producer.intervals;

public class ProducerIntervalDate {

    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
    private String producer;

    public ProducerIntervalDate(String producer, Integer interval, Integer previousWin, Integer followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(Integer previous) {
        this.previousWin = previous;
    }

    public Integer getFollowing() {
        return followingWin;
    }

    public void setFollowing(Integer followingWin) {
        this.followingWin = followingWin;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "ProducerIntervalDate [interval=" + interval + ", previousWin=" + previousWin + ", followingWin=" + followingWin
                + ", producer=" + producer + "]";
    }

}
