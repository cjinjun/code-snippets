package com.code.codesnippets.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author jason on 2018/5/21
 */
@Component
public class consumer1 {
    @KafkaListener(topics = {"${spring.kafka.consumer.topic.EM11}"},containerFactory = "b15H_containerFactory")
    public void  consumer(String msg){
        System.out.println(msg);
    }
}
