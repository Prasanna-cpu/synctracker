package com.spring.synctracker.submission_service.service.abstraction;

import com.spring.synctracker.submission_service.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "TASK-SERVICE", url = "${task-service.url}")
public interface TaskService {

    @GetMapping("/api/tasks/task/{taskId}")
    ApiResponse getTaskByIdHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String taskId
    );

    @PutMapping("/api/tasks/complete-task/{taskId}/user/{userId}")
    ApiResponse completeTaskHandler(
            @PathVariable String taskId,
            @PathVariable String userId,
            @RequestHeader("Authorization")  String jwt
    );

}
