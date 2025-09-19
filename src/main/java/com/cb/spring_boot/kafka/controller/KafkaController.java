package com.cb.spring_boot.kafka.controller;

import com.cb.spring_boot.kafka.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.lang.Thread.getAllStackTraces;

@RestController
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private Buffer buffer;

    @GetMapping("/status")
    public @ResponseBody String status() {
        Map<Thread, StackTraceElement[]> stt = getAllStackTraces();
        Set<String> uniq = new HashSet<>();
        for(Thread t:stt.keySet()){
            ThreadGroup group = t.getThreadGroup();
            System.out.println(t+":");
            StackTraceElement [] ste = stt.get(t);
            for(StackTraceElement st:ste){
                System.out.println("\t"+st);
            }
            System.out.println();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"content\"/>\r\n");
        sb.append("<script>\r\n");
        sb.append("function status(){\r\n");
        sb.append("   const xhttp = new XMLHttpRequest()\r\n");
        sb.append("   xhttp.onload = function() {\r\n");
        sb.append("       document.getElementById(\"content\").innerHTML = this.responseText;\r\n");
        sb.append("   }\r\n");                               //AJAX is now just JAX? :)
        sb.append("   xhttp.open(\"GET\", \"/status_ajax\", false);\r\n");
        sb.append("   xhttp.send();\r\n");
        sb.append("}\r\n");
        sb.append("var intervalId = window.setInterval('status()', 3000);\r\n");
        sb.append("</script>");

        return sb.toString();
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