package com.jason.lee.example.controller;

import cn.hutool.core.util.StrUtil;
import com.jason.lee.example.component.MQProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huanli9
 * @description
 */
@RestController
@RequestMapping("mq")
public class Controller {
    public static final String topic = "TestTopic";

    @Resource
    private MQProducer producer;

    @RequestMapping("sendMessage")
    public String sendMessage(String topic, String message) {
        if (StrUtil.isBlank(topic)) {
            topic = this.topic;
        }
        try {
            producer.sendMessage(topic, message);
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }


    @RequestMapping("sendTransactionMsg")
    public String sendTransactionMsg(String topic, String message) {
        if (StringUtils.isBlank(topic)) {
            topic = this.topic;
        }
        try {
            producer.sendTransactionMsg(topic, message);
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }
}
