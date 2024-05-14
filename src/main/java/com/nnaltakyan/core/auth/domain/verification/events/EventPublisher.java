package com.nnaltakyan.core.auth.domain.verification.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
@Component
public class EventPublisher {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishEvent(String message){
        SendVerificationEmailEvent sendVerificationEmailEvent = new SendVerificationEmailEvent(eventPublisher, message);
        eventPublisher.publishEvent(sendVerificationEmailEvent);
    }
}
