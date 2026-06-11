package com.spring.synctracker.submission_service.serializer;


import com.spring.synctracker.submission_service.dto.TaskDTO;
import com.spring.synctracker.submission_service.mapper.TaskMapper;
import com.spring.synctracker.submission_service.response.ApiResponse;
import com.spring.synctracker.submission_service.service.abstraction.TaskService;
import com.spring.synctracker.submission_service.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class TaskSerializer {

    private final TaskService taskService;

    public TaskDTO serializeTask(String jwt, String taskId){
//        log.info("JWT : {}", jwt);
        ApiResponse apiResponse = taskService.getTaskByIdHandler(jwt, taskId);
        Map<String, Object> data = (Map<String, Object>) apiResponse.getData();
        log.info("Received data: {}", data);
        TaskDTO taskDTO = TaskMapper.convertToTaskDTO(data);
        log.info("Received task data: {}", taskDTO);
        return taskDTO;
    }

    public TaskDTO serializeCompletedTask(String taskId, String userId, String jwt){
        ApiResponse apiResponse = taskService.completeTaskHandler(taskId, userId, jwt);
        Map<String, Object> data = (Map<String, Object>) apiResponse.getData();
        log.info("Received data: {}", data);
        TaskDTO taskDTO = TaskMapper.convertToTaskDTO(data);
        log.info("Received task data: {}", taskDTO);
        return taskDTO;
    }




}
