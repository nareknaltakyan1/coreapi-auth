package com.nnaltakyan.core.auth.domain.verification.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishEvent(SendVerificationEmailEvent emailEvent){
        log.info("Email verification event has been thrown for user: {}.", emailEvent.getUserId());
        eventPublisher.publishEvent(emailEvent);
    }
}
