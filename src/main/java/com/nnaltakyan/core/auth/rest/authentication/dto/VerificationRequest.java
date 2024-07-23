package com.nnaltakyan.core.auth.rest.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequest
{
	@NotBlank
	// add integer check
	String verificationCode;
	String email;
}
