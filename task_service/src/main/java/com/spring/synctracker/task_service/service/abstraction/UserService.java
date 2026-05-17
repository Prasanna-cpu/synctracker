package com.spring.synctracker.task_service.service.abstraction;


import com.spring.synctracker.task_service.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "${user-service.url}")
public interface UserService {

    @GetMapping("/api/users/profile")
    ApiResponse getUserProfileFromAuthenticationHandler(@RequestHeader("Authorization") String jwt);

}
