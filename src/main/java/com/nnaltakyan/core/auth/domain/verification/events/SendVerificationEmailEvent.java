package com.nnaltakyan.core.auth.domain.verification.events;

import com.nnaltakyan.core.auth.domain.auth.service.AuthenticationService;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SendVerificationEmailEvent extends ApplicationEvent {

    private final Long userId;
    public SendVerificationEmailEvent(AuthenticationService source, Long userId) {
        super(source);
        this.userId = userId;
    }
}
