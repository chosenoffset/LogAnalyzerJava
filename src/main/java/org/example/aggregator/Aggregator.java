package org.example.aggregator;

import java.util.Map;

public class Aggregator {
    Map<String, Integer> counts;

    public Aggregator() {
        counts = new java.util.HashMap<>();
    }

    public void AddAggregator(String name) {
        AddAggregator(name, 0);
    }

    public void AddAggregator(String name, int initialValue) {
        counts.put(name, initialValue);
    }

    public void Increment(String name) {
        counts.put(name, counts.get(name) + 1);
    }

    public String PrintCounts() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
