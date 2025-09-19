package com.cb.spring_boot.kafka.consumer;

import com.cb.spring_boot.kafka.controller.Buffer;
import com.cb.spring_boot.kafka.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    @Autowired
    private Buffer buffer;

    @KafkaListener(topics = "status")//, groupId = "my-group-id")
    public void listen(String message) {
        buffer.add(message);
        System.out.println("Received message: " + message+". Put in buffer:"+buffer+". Size now: "+buffer.size());
    }

}