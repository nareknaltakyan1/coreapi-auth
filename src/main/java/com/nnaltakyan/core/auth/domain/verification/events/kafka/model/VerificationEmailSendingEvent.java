package com.nnaltakyan.core.auth.domain.verification.events.kafka.model;

import com.nnaltakyan.core.auth.rest.enums.EmailType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationEmailSendingEvent {
    String email;
    EmailType emailType;
    Long otp;
    Long userId;
}
