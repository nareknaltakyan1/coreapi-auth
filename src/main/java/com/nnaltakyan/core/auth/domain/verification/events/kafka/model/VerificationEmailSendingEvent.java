package com.nnaltakyan.core.auth.domain.verification.events.kafka.model;

import com.nnaltakyan.api.core.common.domain.EmailType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationEmailSendingEvent
{
	String email;
	EmailType emailType;
	Long verificationCode;
	Long userId;
}
