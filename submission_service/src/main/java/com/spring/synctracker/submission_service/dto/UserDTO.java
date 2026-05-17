package com.spring.synctracker.submission_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.synctracker.submission_service.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;

    private String email;

    private String fullName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Role role;
}
