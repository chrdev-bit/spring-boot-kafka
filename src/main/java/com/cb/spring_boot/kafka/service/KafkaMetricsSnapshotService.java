package com.cb.spring_boot.kafka.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaMetricsSnapshotService {

    private volatile Consumer<?, ?> consumer;
    private volatile Producer<?, ?> producer;

    private final Map<String, Double> snapshot = new ConcurrentHashMap<>();

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
            Object value = metric.metricValue();

            if (value instanceof Number) {
                snapshot.put(key, ((Number) value).doubleValue());
            }
        }
    }
    //producer-metrics.batch-size-avg{client-id=spring-boot-producer-1} = 117.0
    private String buildKey(MetricName name) {
        return name.group() + "." + name.name();// + name.tags().toString();
    }
}
