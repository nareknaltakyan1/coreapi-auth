package com.nnaltakyan.core.auth.domain.verification.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class VerificationException extends RuntimeException{
    private String message;
}
