package com.cb.spring_boot.kafka.controller;

import com.cb.spring_boot.kafka.producer.MessageProducer;
import com.cb.spring_boot.kafka.service.KafkaMetricsSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private KafkaMetricsSnapshotService snapshotService;

    @Autowired
    private Buffer buffer;

    @GetMapping("/")
    public @ResponseBody String home() {
        return "Hello Kafka";
    }

    @GetMapping("/api/kafka-metrics")
    public Map<String, Double> getKafkaMetrics() {
        return snapshotService.getSnapshot();
    }

    @GetMapping("/status_ajax")
    public @ResponseBody String status_ajax() {
        System.out.println("status_ajax(): buffer obj:"+buffer+": buffer size():"+buffer.size());
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>"+buffer.size()+" hosts monitored</h3>");
        for(String msg:buffer.getHosts()){
            try {
                System.out.println("status_ajax() > " + msg);
                String[] data = msg.split("\\|");
                sb.append(data[0] + "&nbsp;&nbsp;" + data[1] + " KB&nbsp;&nbsp;" + data[2] + "<br/>");
            }catch(Throwable t){
                System.err.println(t+": msg:"+msg);
                t.printStackTrace();
            }
        }
        return sb.toString();
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        messageProducer.sendStatus("status", message);
        return "Message sent: " + message;
    }

}