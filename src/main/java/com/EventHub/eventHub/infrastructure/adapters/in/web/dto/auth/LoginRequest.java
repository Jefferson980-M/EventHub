package com.EventHub.eventHub.infrastructure.adapters.in.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "{auth.username.notblank}")
    private String username;

    @NotBlank(message = "{auth.password.notblank}")
    private String password;
}
