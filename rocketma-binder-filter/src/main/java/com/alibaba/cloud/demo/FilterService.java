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

import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@Service
public class FilterService {

    @ServiceActivator(inputChannel = FilterApplication.DISCARD_INPUT)
    public void discardMsg(String receiveMsg) {
        System.out.println("msg discard by filter: " + receiveMsg);
    }

    @ServiceActivator(inputChannel = FilterApplication.SUCCESS_INPUT)
    public void successMsg(String receiveMsg) {
        System.out.println("msg not discard by filter: " + receiveMsg);
    }

    @Filter(inputChannel = Sink.INPUT, discardChannel = FilterApplication.DISCARD_INPUT, outputChannel = FilterApplication.SUCCESS_INPUT)
    public boolean receiveByFilter(String receiveMsg) {
        String[] msgInfo = receiveMsg.split("-");
        if (Integer.parseInt(msgInfo[1]) % 2 == 0) {
            return true;
        }
        return false;
    }
}