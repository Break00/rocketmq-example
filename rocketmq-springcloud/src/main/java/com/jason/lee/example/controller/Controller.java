package com.jason.lee.example.controller;

import com.jason.lee.example.component.MQProducer;
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
    @Resource
    private MQProducer producer;

    @RequestMapping("sendMessage")
    public String sendMessage(String message) {
        try {
            producer.sendMessage(message);
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }

}
