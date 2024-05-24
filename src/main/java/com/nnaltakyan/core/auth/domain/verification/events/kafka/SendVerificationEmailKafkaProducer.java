package com.nnaltakyan.core.auth.domain.verification.events.kafka;

import com.nnaltakyan.core.auth.domain.verification.events.kafka.model.VerificationEmailSendingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendVerificationEmailKafkaProducer {
    private final KafkaTemplate<String, VerificationEmailSendingEvent> kafkaTemplate;

    public void sendMessage(String topic, VerificationEmailSendingEvent message) {
        kafkaTemplate.send(topic, message);
    }
}
