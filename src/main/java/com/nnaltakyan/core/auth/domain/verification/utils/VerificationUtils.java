package com.nnaltakyan.core.auth.domain.verification.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Slf4j
@Component
public class VerificationUtils
{

	public static final int AMOUNT_OF_DIGITS = 6;

	public static String generateVerificationCode()
	{
		final SecureRandom random = new SecureRandom();
		final StringBuilder verificationCode = new StringBuilder(AMOUNT_OF_DIGITS);
		for (int i = 0; i < AMOUNT_OF_DIGITS; i++)
		{
			verificationCode.append(random.nextInt(10));
		}
		return verificationCode.toString();
	}
}
