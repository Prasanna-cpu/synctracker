package com.spring.synctracker.user_service.mapper;

import com.spring.synctracker.user_service.dto.UserDTO;
import com.spring.synctracker.user_service.entity.User;

public class UserMapper {

    public static UserDTO mapUserToUserDTO(User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        return userDTO;

    }

    public static User mapUserDTOToUser(UserDTO userDTO) {

        User user = new User();

        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;

    }

}
