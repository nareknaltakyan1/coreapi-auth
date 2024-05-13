package com.nnaltakyan.core.auth.domain.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_verifiction")
public class Verification {
    private String user_id;
    private String verification_code;
    private boolean created_time;
    private boolean verified;
}
