package com.nnaltakyan.core.auth.domain.verification.listener;

import com.nnaltakyan.api.core.common.domain.EmailType;
import com.nnaltakyan.api.core.common.domain.Topic;
import com.nnaltakyan.api.core.common.exceptions.UserNotFoundException;
import com.nnaltakyan.api.core.common.exceptions.VerificationFailedException;
import com.nnaltakyan.core.auth.domain.user.model.User;
import com.nnaltakyan.core.auth.domain.user.service.UserRepository;
import com.nnaltakyan.core.auth.domain.verification.events.SendVerificationEmailEvent;
import com.nnaltakyan.core.auth.domain.verification.events.kafka.SendVerificationEmailKafkaProducer;
import com.nnaltakyan.core.auth.domain.verification.events.kafka.model.VerificationEmailSendingEvent;
import com.nnaltakyan.core.auth.domain.verification.model.Verification;
import com.nnaltakyan.core.auth.domain.verification.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.nnaltakyan.api.core.common.error.ErrorMessage.USER_NOT_FOUND;
import static com.nnaltakyan.api.core.common.error.ErrorMessage.VERIFICATION_FAILED;

@Component
@Slf4j
@AllArgsConstructor
public class SendVerificationEmailEventListener
{

	private final UserRepository userRepository;
	private final VerificationRepository verificationRepository;
	private final SendVerificationEmailKafkaProducer kafkaProducer;

	@TransactionalEventListener
	@Async
	public void listener(final SendVerificationEmailEvent event)
	{
		log.info("A kafka event for verifying the email of the user with id {} is published", event.getUserId());
		var user = userRepository.findById(event.getUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));
		var verification = verificationRepository.findByUserid(event.getUserId())
			.orElseThrow(() -> new VerificationFailedException(VERIFICATION_FAILED.getMessage()));
		kafkaProducer.sendMessage(Topic.EMAIL_VERIFICATION.getName(), buildEvent(user, verification));
	}

	private VerificationEmailSendingEvent buildEvent(final User user, final Verification verification)
	{
		return VerificationEmailSendingEvent.builder().fromEmail(null).toEmail(user.getEmail()).emailType(EmailType.VERIFICATION).userId(user.getId())
			.verificationCode(verification.getCode()).build();
	}
}
