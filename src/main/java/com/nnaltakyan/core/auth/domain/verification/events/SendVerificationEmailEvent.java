package com.nnaltakyan.core.auth.domain.verification.events;

import org.springframework.context.ApplicationEvent;

public class SendVerificationEmailEvent extends ApplicationEvent {
    private String message;
    public SendVerificationEmailEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
