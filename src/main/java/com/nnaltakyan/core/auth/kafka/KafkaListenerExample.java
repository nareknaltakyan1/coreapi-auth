package com.nnaltakyan.core.auth.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListenerExample {

    @KafkaListener(topics = "topic-1", groupId = "group1")
    void listener(String data) {
        log.info("Received message [{}] in group1", data);
    }
}