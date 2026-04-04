package com.cb.spring_boot.kafka.model;

import java.util.HashMap;
import java.util.Map;

public class KafkaMetricsSnapshot {

    private final Map<String, Double> metrics = new HashMap<>();
    private long timestamp;

    public void put(String key, double value) {
        metrics.put(key, value);
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
