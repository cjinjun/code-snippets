package com.code.codesnippets.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author jason on 2018/5/21
 */
@Component
public class consumer {
    @KafkaListener(topics = {"${spring.kafka.consumer.topic.EM11}"})
    public void  consumer(String msg){
        System.out.println(msg);
    }
}
