/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.demo;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@Service
public class AggregatorService {

    @ServiceActivator(inputChannel = AggregatorApplication.DISCARD_INPUT)
    public void discardMsg(String receiveMsg) {
        System.out.println("msg discard by aggregator: " + receiveMsg);
    }

    @ServiceActivator(inputChannel = AggregatorApplication.TEMP_INPUT)
    public String tempMsg(String receiveMsg) {
        System.out.println("msg handle by aggregator: " + receiveMsg);
        return receiveMsg;
    }

    // 聚合规则（返回值相同被视为同一组消息）
    @CorrelationStrategy
    public Integer correlationStrategy(Message message) {
        String s = (String) message.getPayload();
        return Integer.parseInt(s.split("-")[1])
                % 2;
    }

    // 发送规则 （聚合终止条件）
    @ReleaseStrategy
    public boolean releaseStrategy(Collection<Message> messageCollection) {
        return messageCollection.size() >= 3;
    }

    // 聚合器 （inputChannel：消息原始来源  outputChannel：聚合处理后消息流向）
    @Aggregator(inputChannel = Sink.INPUT, outputChannel = AggregatorApplication.TEMP_INPUT)
    public String receive(List<Message> messageList) {
        // 对聚合后的信息处理
        Random random = new Random();
        int batch = random.nextInt(100000);
        for (Message msg : messageList) {
            System.out.println(batch + " === " + new String((byte[]) msg.getPayload()));
        }
        return "aggregator result[" + batch + "]";
    }

}
