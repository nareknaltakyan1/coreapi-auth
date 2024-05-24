package com.nnaltakyan.core.auth.rest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailType {
    VERIFICATION("verification");

    private final String emailType;
}
