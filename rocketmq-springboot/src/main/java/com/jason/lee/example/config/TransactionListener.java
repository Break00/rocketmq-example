package com.jason.lee.example.config;

import cn.hutool.core.util.StrUtil;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huanli9
 * @description
 */
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate") //rocketmq-spring-boot-starter 2.2.2版本 rocketmq 4.8.0
//@RocketMQTransactionListener(txProducerGroup = "TransactionProducerGroup")  rocketmq-spring-boot-starter 2.0.2版本 rocketmq 4.4.0
public class TransactionListener implements RocketMQLocalTransactionListener {

    private ConcurrentHashMap<Object, Message> localTrans = new ConcurrentHashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        Object transId = msg.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID);
        localTrans.put(transId, msg);
        System.out.println("executeLocalTransaction msg=" + msg);
        // todo RocketMQ的Tag被框架封装后带前缀 RocketMQHeaders.PREFIX
        String tags = msg.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS).toString();
        if (StrUtil.equals(tags, "TagA")) {
            return RocketMQLocalTransactionState.COMMIT;
        } else if (StrUtil.equals(tags, "TagB")) {
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        Object transId = msg.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID);
        Message originalMessage = localTrans.get(transId);
        System.out.println("checkLocalTransaction msg=" + originalMessage);
        String tags = msg.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS).toString();
        if (StrUtil.equals(tags, "TagC")) {
            return RocketMQLocalTransactionState.COMMIT;
        } else if (StrUtil.equals(tags, "TagD")) {
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
