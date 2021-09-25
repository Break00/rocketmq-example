package com.jason.lee.example.component;

import cn.hutool.core.date.DateUtil;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huanli9
 * @description
 */
@Component
public class MQProducer {
    @Resource  // 可以自定义template，并设置生产者的相关参数 如：groupName
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic, String msg) {
        this.rocketMQTemplate.convertAndSend(topic, msg);
    }

    public void sendTransactionMsg(String topic, String msg) {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload(msg).build();
            // destination = topic:tag
            String destination = topic + ":" + tags[i % tags.length];
            // rocketmq-spring-boot-starter 2.0.2版本   rocketmq客户端 V4.4版本
            // SendResult sendResult = rocketMQTemplate.sendMessageInTransaction("TransactionProducerGroup",destination, message, null);
            SendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, null);
            System.out.printf("%s%n", sendResult);
        }
    }

}
