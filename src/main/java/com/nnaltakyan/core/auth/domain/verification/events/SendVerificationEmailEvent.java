package com.nnaltakyan.core.auth.domain.verification.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendVerificationEmailEvent extends ApplicationEvent {
    private final Long userId;
    public SendVerificationEmailEvent(Object source, String userId) {
        super(source);
        this.userId = Long.valueOf(userId);
    }

}
