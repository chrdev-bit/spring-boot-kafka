package com.cb.spring_boot.kafka.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class Buffer{
    Map<String,String> hosts = new HashMap();

    public synchronized void add(String msg){
        hosts.put(msg.split("\\|")[0],msg);
    }

    public synchronized Collection<String> getHosts(){
        return hosts.values();
    }

    public int size() {
        return hosts.size();
    }
}
