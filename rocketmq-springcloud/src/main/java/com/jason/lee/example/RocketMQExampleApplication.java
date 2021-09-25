package com.jason.lee.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

/**
 * @author huanli9
 * @description
 */
@EnableBinding({Source.class, Sink.class})
@SpringBootApplication
public class RocketMQExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketMQExampleApplication.class, args);
    }

}