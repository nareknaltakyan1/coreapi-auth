package com.nnaltakyan.core.auth.domain.user.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserNotFoundException extends RuntimeException{
    private String message;
}
