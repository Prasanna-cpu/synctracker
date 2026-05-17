package com.spring.synctracker.user_service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    @NotNull(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @NotNull(message = "Password is required")
    private String password;
}
