package com.spring.synctracker.user_service.request;

import com.spring.synctracker.user_service.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Register {

    @NotNull(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String confirmPassword;

    @NotNull(message = "Full name is required")
    @NotEmpty(message = "Full name is required")
    @Size(min = 3, message = "Full name must be at least 3 characters long")
    private String fullName;

    @NotNull(message = "Role is required")
    @NotEmpty(message = "Role is required")
    @Size(min = 3, message = "Role must be at least 3 characters long")
    private Role role;
}
