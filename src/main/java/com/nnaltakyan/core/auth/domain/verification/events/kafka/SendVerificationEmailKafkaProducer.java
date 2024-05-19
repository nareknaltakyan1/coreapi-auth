package com.nnaltakyan.core.auth.domain.verification.events.kafka;

import com.nnaltakyan.core.auth.domain.verification.events.kafka.model.VerificationEmailSendingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendVerificationEmailKafkaProducer {
    @Autowired
    private KafkaTemplate<String, VerificationEmailSendingEvent> kafkaTemplate;

    public void sendMessage(String topic, VerificationEmailSendingEvent message) {
        kafkaTemplate.send(topic, message);
    }
}
