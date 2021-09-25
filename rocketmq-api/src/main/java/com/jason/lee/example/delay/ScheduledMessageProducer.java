package com.jason.lee.example.delay;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author huanli9
 * @description  Broker启动时默认创建18个定时任务负责处理延时消息 （对应18种delayLevel）
 *                  Broker首先将延时消息的信息存放在SCHEDULE_TOPIC_XXXX下（队列序号为delayLevel-1）
 *                  定时任务将到期消息的信息存放到该消息原本的Topic对应的队列下
 * @date 2021/7/16 10:34
 */
public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        // Launch producer
        producer.start();
        int totalMessagesToSend = 100;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            // 1s 5 10 30 1m 2 3 4 5 6 7 8 9 10 20 30 1h 2h  todo 定制化延时时间？
            message.setDelayTimeLevel(1);
            // Send the message
            producer.send(message);
        }
        // Shutdown producer after use.
        producer.shutdown();
    }
}