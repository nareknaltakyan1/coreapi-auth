package com.nnaltakyan.core.auth.domain.verification.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishEvent(String message){
        SendVerificationEmailEvent sendVerificationEmailEvent = new SendVerificationEmailEvent(eventPublisher, message);
        eventPublisher.publishEvent(sendVerificationEmailEvent);
    }
}
