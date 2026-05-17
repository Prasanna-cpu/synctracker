package com.spring.synctracker.task_service.service.abstraction;

import com.spring.synctracker.task_service.dto.TaskDTO;
import com.spring.synctracker.task_service.entity.Task;
import com.spring.synctracker.task_service.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO,  String requestRole);

    TaskDTO getTaskById(String Id);

    List<TaskDTO> getAllTasks();

    List<TaskDTO> getTasksByStatus(TaskStatus status);

    TaskDTO updateTask(String Id, TaskDTO updatedTask);

    void deleteTask(String Id);

    TaskDTO assignTaskToUser(String userId, String taskId);

    List<TaskDTO> assignedUsersTask(String userId, TaskStatus status);

    TaskDTO completeTask(String taskId, String userId);

}
