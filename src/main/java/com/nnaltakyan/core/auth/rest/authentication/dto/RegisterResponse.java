package com.nnaltakyan.core.auth.rest.authentication.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private Long userId;
    private String email;
    private String status;
}
