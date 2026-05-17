package com.spring.synctracker.task_service.mapper;

import com.spring.synctracker.task_service.dto.UserDTO;
import com.spring.synctracker.task_service.enums.Role;

import java.util.Map;

public class UserMapper {
    public static UserDTO convertToUserDTO(Map<String, Object> data){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(data.get("id") != null ? (String) data.get("id") : null);
        userDTO.setEmail(data.get("email") != null ? (String) data.get("email") : null);
        userDTO.setFullName(data.get("fullName") != null ? (String) data.get("fullName") : null);
        if (data.get("role") != null) {
            userDTO.setRole(Role.valueOf((String) data.get("role")));
        }
        return userDTO;
    }
}
