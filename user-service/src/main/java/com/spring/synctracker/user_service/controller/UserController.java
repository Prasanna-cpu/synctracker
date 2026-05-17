package com.spring.synctracker.user_service.controller;


import com.spring.synctracker.user_service.dto.UserDTO;
import com.spring.synctracker.user_service.response.ApiResponse;
import com.spring.synctracker.user_service.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfileFromAuthenticationHandler(@RequestHeader("Authorization") String jwt) {
        UserDTO userDTO = userService.getUserProfileFromAuthentication(jwt);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(
                        "User profile fetched successfully",
                        userDTO,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                ));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserByIdHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String id
    ){
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(
                        "User fetched successfully",
                        userDTO,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                ));
    }

    @GetMapping("/all-users")
    public ResponseEntity<ApiResponse> getAllUsersHandler(
            @RequestHeader("Authorization") String jwt
    ){
        List<UserDTO> userDTOList = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(
                        "All users fetched successfully",
                        userDTOList,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                ));
    }

    @GetMapping("/user-by-email/{email}")
    public ResponseEntity<ApiResponse> getUserByEmailHandler(
            @PathVariable String email
    ){
        UserDTO userDTO = userService.getUserByEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(
                        "User fetched successfully",
                        userDTO,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                ));
    }

}
