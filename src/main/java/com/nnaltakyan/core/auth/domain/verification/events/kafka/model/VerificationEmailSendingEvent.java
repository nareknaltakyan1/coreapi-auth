package com.nnaltakyan.core.auth.domain.verification.events.kafka.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationEmailSendingEvent {
    String email;
    String emailType;
    Long otp;
    Long userId;
}
