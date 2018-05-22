package com.code.codesnippets.kafka.producter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author jason on 2018/5/22
 */
@Component
public class producter {
    @Autowired
    KafkaTemplate kafkaTemplate;
    public void send(){
        kafkaTemplate.sendDefault("发送一个消息");
    }
}

