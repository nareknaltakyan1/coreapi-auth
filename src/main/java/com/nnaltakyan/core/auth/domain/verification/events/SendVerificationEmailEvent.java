package com.nnaltakyan.core.auth.domain.verification.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class SendVerificationEmailEvent
{

	private final Long userId;

}
