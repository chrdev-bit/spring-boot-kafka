package com.cb.spring_boot.kafka.service;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@ConfigurationProperties(prefix = "app")
public class KafkaMetricsSnapshotService {

    private volatile Consumer<?, ?> consumer;
    private volatile Producer<?, ?> producer;

    private final Map<String, Double> snapshot = new ConcurrentHashMap<>();
    private Set<String> allowedKeys;
    private Set<String> cachedKeys;

    public void setAllowedKeys(Set<String> allowedKeys) {
        this.allowedKeys = allowedKeys;
    }

    @PostConstruct
    public void init() {
        cachedKeys = allowedKeys == null ? Set.of() :
                allowedKeys.stream()
                        .map(String::trim)
                        .collect(Collectors.toUnmodifiableSet());
    }

    public void setConsumer(Consumer<?, ?> consumer) {
        this.consumer = consumer;
    }

    public void setProducer(Producer<?, ?> producer) {
        this.producer = producer;
    }

    public Map<String, Double> getSnapshot() {
        return snapshot;
    }

    public void refresh() {
        snapshot.clear();

        if (producer != null) {
            extractMetrics(producer.metrics());
        }
        if (consumer != null) {
            extractMetrics(consumer.metrics());
        }
    }

    private void extractMetrics(Map<MetricName, ? extends Metric> metrics) {
        for (Map.Entry<MetricName, ? extends Metric> entry : metrics.entrySet()) {
            MetricName name = entry.getKey();
            Metric metric = entry.getValue();

            String key = buildKey(name);

            if (!cachedKeys.contains(key)) {
                continue;
            }

            Object value = metric.metricValue();

            if (value instanceof Number) {
                snapshot.put(key, ((Number) value).doubleValue());
            }
        }
    }

    private String buildKey(MetricName name) {
        return name.group() + "." + name.name();
    }
}