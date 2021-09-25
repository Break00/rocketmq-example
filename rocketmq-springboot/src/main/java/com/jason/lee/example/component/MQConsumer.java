package com.jason.lee.example.component;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author huanli9
 * @description
 */
@Component
@RocketMQMessageListener(consumerGroup = "ConsumeGroup", topic = "TestTopic", consumeMode = ConsumeMode.CONCURRENTLY)
public class MQConsumer implements RocketMQListener<Object> {
    @Override
    public void onMessage(Object message) {
        System.out.println("Received message: " + message);
    }
}
