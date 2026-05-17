package com.spring.synctracker.submission_service.serializer;

import com.spring.synctracker.submission_service.dto.UserDTO;
import com.spring.synctracker.submission_service.mapper.UserMapper;
import com.spring.synctracker.submission_service.response.ApiResponse;
import com.spring.synctracker.submission_service.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserSerializer {

    private final UserService userService;

    public UserDTO serializeUser(String jwt){
        ApiResponse apiResponse = userService.getUserProfileFromAuthenticationHandler(jwt);
        Map<String, Object> data = (Map<String, Object>) apiResponse.getData();
        log.info("Serialized data: {}", data);
        UserDTO userDTO = UserMapper.convertToUserDTO(data);
        log.info("Serialized user data: {}", userDTO);
        return userDTO;
    }
}
