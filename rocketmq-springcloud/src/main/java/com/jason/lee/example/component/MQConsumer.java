package com.jason.lee.example.component;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * @author huanli9
 * @description
 */
@Component
public class MQConsumer {

    @StreamListener(value = Sink.INPUT)
    public void onMessage(String message) {
        System.out.println("Received message: " + message + " from binding: " + Sink.INPUT);
    }
}
