package com.nnaltakyan.core.auth.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum UserStatus {
    CREATED ("CREATED"),
    VERIFIED ("VERIFIED");
    private String status;
}
