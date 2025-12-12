package com.EventHub.eventHub.infrastructure.adapters.in.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "{auth.username.notblank}")
    @Size(min = 3, max = 20, message = "{auth.username.size}")
    private String username;

    @NotBlank(message = "{auth.password.notblank}")
    @Size(min = 6, max = 40, message = "{auth.password.size}")
    private String password;
}
