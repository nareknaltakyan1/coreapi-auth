package com.nnaltakyan.core.auth.domain.verification.listener;

import com.nnaltakyan.api.core.common.domain.EmailType;
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
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.Objects;
import static com.nnaltakyan.api.core.common.domain.Topic.EMAIL_VERIFICATION;
import static com.nnaltakyan.api.core.common.error.ErrorMessages.USER_NOT_FOUND;

@Component
@Slf4j
@AllArgsConstructor
public class EmailVerificationListener implements ApplicationListener<SendVerificationEmailEvent>
{

	private final UserRepository userRepository;
	private final VerificationRepository verificationRepository;
	private final SendVerificationEmailKafkaProducer kafkaProducer;
	private final static String VERIFICATION_NOT_FOUND = "Verification record not found.";

	@Override
	public void onApplicationEvent(SendVerificationEmailEvent event)
	{
		log.info("A kafka event for verifying the email of the user with id {} is published", event.getUserId());
		User user = userRepository.findById(event.getUserId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND.getMessage()));
		Verification verification = verificationRepository.findByUserid(event.getUserId())
			.orElseThrow(() -> new VerificationFailedException(VERIFICATION_NOT_FOUND));
		if (Objects.nonNull(user) && Objects.nonNull(verification))
		{
			VerificationEmailSendingEvent verificationEmailSendingEvent = VerificationEmailSendingEvent.builder().userId(user.getId())
				.email(user.getEmail()).emailType(EmailType.VERIFICATION).verificationCode(verification.getVerificationCode()).build();
			kafkaProducer.sendMessage(EMAIL_VERIFICATION.name(), verificationEmailSendingEvent);
		}
	}
}
