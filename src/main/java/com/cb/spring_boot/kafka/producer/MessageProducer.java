package com.cb.spring_boot.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableScheduling
@Service
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 3000)
    public void performTask() throws UnknownHostException {
        String msg = InetAddress.getLocalHost().getHostAddress()+":"
                        +System.getProperty("server.port")+"|"
                            +(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024l+"|"
                                +new java.util.Date();
        sendStatus("status",msg);
        System.out.println(this+": sent "+msg);
    }

    public void sendStatus(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

}