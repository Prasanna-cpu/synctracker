package com.spring.synctracker.submission_service.service.abstraction;


import com.spring.synctracker.submission_service.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:2000")
public interface UserService {

    @GetMapping("/api/users/profile")
    ApiResponse getUserProfileFromAuthenticationHandler(@RequestHeader("Authorization") String jwt);

}
