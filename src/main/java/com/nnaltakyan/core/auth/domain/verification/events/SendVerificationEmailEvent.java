package com.nnaltakyan.core.auth.domain.verification.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendVerificationEmailEvent extends ApplicationEvent {

    private final Long userId;
    public SendVerificationEmailEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }
}
