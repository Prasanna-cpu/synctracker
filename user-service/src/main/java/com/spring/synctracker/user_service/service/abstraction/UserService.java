package com.spring.synctracker.user_service.service.abstraction;

import com.spring.synctracker.user_service.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserProfileFromAuthentication(String jwt);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(String id);

    UserDTO getUserByEmail(String email);
}
