package com.nnaltakyan.core.auth.domain.verification.advice;

import com.nnaltakyan.api.core.common.domain.ErrorMessage;
import com.nnaltakyan.api.core.common.exceptions.UserNotFoundException;
import com.nnaltakyan.api.core.common.exceptions.VerificationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{

	@ExceptionHandler(VerificationFailedException.class)
	public ResponseEntity<String> handleException(VerificationFailedException ex)
	{
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessage.VERIFICATION_NOT_FOUND.getMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleNotFoundException(UserNotFoundException ex)
	{
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.USER_NOT_FOUND.getMessage());
	}
}
