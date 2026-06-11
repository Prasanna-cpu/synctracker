package com.spring.synctracker.task_service.controller;

import com.spring.synctracker.task_service.dto.TaskDTO;
import com.spring.synctracker.task_service.dto.UserDTO;
import com.spring.synctracker.task_service.entity.Task;
import com.spring.synctracker.task_service.enums.TaskStatus;
import com.spring.synctracker.task_service.response.ApiResponse;
import com.spring.synctracker.task_service.serializer.UserSerializer;
import com.spring.synctracker.task_service.service.abstraction.TaskService;
import com.spring.synctracker.task_service.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final UserSerializer userSerializer;

    @PostMapping("/create-task")
    public ResponseEntity<ApiResponse> createTaskHandler(
            @RequestHeader("Authorization") String jwt,
            @RequestBody TaskDTO taskDTO
    ){
//        UserDTO userDTO = userSerializer.serializeUser(jwt);
        TaskDTO createdTaskDTO = taskService.createTaskWithAuth(taskDTO, jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(
                        "New Task Created",
                        createdTaskDTO,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED
                )
        );
    }


    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse> getTaskByIdHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String taskId
    ){
        TaskDTO taskDTO = taskService.getTaskById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Task retrieved",
                        taskDTO,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/assigned-tasks/user/{userId}")
    public ResponseEntity<ApiResponse> getAssignedUserId(
            @RequestParam(name = "status") TaskStatus status,
            @RequestHeader("Authorization") String jwt,
            @PathVariable String userId
    ){
//        UserDTO userDTO = userSerializer.serializeUser(jwt);
        List<TaskDTO> assignedTasks = taskService.assignedUsersTask(userId, status);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Task retrieved",
                        assignedTasks,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/all-tasks")
    public ResponseEntity<ApiResponse> getAllTasksHandler(
            @RequestHeader("Authorization") String jwt
    ){
        List<TaskDTO> taskDTOS = taskService.getAllTasks();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "All Tasks retrieved",
                        taskDTOS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/tasks-by-status")
    public ResponseEntity<ApiResponse> getAllTasksByStatusHandler(
            @RequestParam(name = "status") TaskStatus status,
            @RequestHeader("Authorization") String jwt
    ){
        List<TaskDTO> taskDTOS = taskService.getTasksByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "All Tasks retrieved",
                        taskDTOS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("/task/{taskId}/assign-to-user/{userId}")
    public ResponseEntity<ApiResponse> assignTaskToUserHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String taskId,
            @PathVariable String userId
    ){
//        UserDTO userDTO = userSerializer.serializeUser(jwt);
        TaskDTO taskDTO = taskService.assignTaskToUser(userId, taskId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Task Assigned to the required user",
                        taskDTO,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("/update-task/{taskId}")
    public ResponseEntity<ApiResponse> updateTaskHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String taskId,
            @RequestBody TaskDTO taskDTO
    ){
//        UserDTO userDTO = userSerializer.serializeUser(jwt);
        TaskDTO updatedTaskDTO = taskService.updateTask(taskId, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Task Updated",
                        updatedTaskDTO,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("/delete-task/{taskId}")
    public ResponseEntity<ApiResponse> deleteTaskHandler(
            @PathVariable String taskId,
            @RequestHeader("Authorization")  String jwt
    ){
        taskService.deleteTask(taskId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse(
                                "Task deleted",
                                null,
                                HttpStatus.OK.value(),
                                HttpStatus.OK
                        )
                );
    }

    @PutMapping("/complete-task/{taskId}/user/{userId}")
    public ResponseEntity<ApiResponse> completeTaskHandler(
            @PathVariable String taskId,
            @PathVariable String userId,
            @RequestHeader("Authorization")  String jwt
    ){
//        UserDTO userDTO = userSerializer.serializeUser(jwt);
        TaskDTO completedTaskDTO = taskService.completeTask(taskId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Task completed",
                        completedTaskDTO,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }



}
