package com.cb.spring_boot.kafka.producer;

import com.cb.spring_boot.kafka.service.KafkaMetricsSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableScheduling
@Service
public class MessageProducer {
    private final KafkaMetricsSnapshotService service;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public MessageProducer(KafkaMetricsSnapshotService service) {
        this.service = service;
    }

    @Scheduled(fixedRate = 3000)
    public void performTask() throws UnknownHostException {
        String msg = InetAddress.getLocalHost().getHostAddress()+":"
                        +System.getProperty("server.port")+"|"
                            +(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024l+"|"
                                +new java.util.Date();
        sendStatus("status",msg);
        service.refresh();
        service.getSnapshot().forEach((k, v) ->
                System.out.println(k + " = " + v)
        );
        System.out.println(this+": sent "+msg);
    }

    public void sendStatus(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

}