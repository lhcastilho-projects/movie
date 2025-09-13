package com.lhcastilho.movie.producer.intervals;

import java.util.ArrayList;
import java.util.List;

public class Finder {

    private int min;

    private int max;

    private List<ProducerIntervalDate> minElements;
    
    private List<ProducerIntervalDate> maxElements;

    public void findIntervals(List<Integer> years, String producer) {
        max = 0;
        min = Integer.MAX_VALUE;
        for (int i = 0; i < years.size() - 1; i++) {
            int before = years.get(i);
            int after = years.get(i + 1);

            int partialMin = after - before;
            if (partialMin < min) {
                min = partialMin;
                minElements = new ArrayList<>();
                minElements.add(new ProducerIntervalDate(producer, min, before, after));
            } else if (partialMin == min) {
                minElements.add(new ProducerIntervalDate(producer, min, before, after));
            }

            int partialMax = after - years.get(0);
            if (partialMax > max) {
                max = partialMax;
                maxElements = new ArrayList<>();
                maxElements.add(new ProducerIntervalDate(producer, max, before, after));
            }
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public List<ProducerIntervalDate> getMinElements() {
        return minElements;
    }

    public List<ProducerIntervalDate> getMaxElements() {
        return maxElements;
    }

}
