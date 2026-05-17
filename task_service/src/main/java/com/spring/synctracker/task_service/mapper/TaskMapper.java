package com.spring.synctracker.task_service.mapper;

import com.spring.synctracker.task_service.dto.TaskDTO;
import com.spring.synctracker.task_service.entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskMapper {

    public static TaskDTO mapToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setImage(task.getImage());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setAssignedUserId(task.getAssignedUserId());
        taskDTO.setTags(task.getTags());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setTaskCreatedAt(task.getTaskCreatedAt());
        taskDTO.setStatus(task.getStatus());
        return taskDTO;
    }

    public static Task mapToTask(TaskDTO taskDTO){
        Task task = new Task();
//        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setImage(taskDTO.getImage());
        task.setDeadline(taskDTO.getDeadline());
        task.setAssignedUserId(taskDTO.getAssignedUserId());
        task.setTags(taskDTO.getTags());
        task.setDescription(taskDTO.getDescription());
//        task.setTaskCreatedAt(LocalDate.now());
        task.setStatus(taskDTO.getStatus());
        return task;
    }

    public static void updateTaskFromDTO(Task task, TaskDTO taskDTO) {
        if (taskDTO.getTitle() != null) {
            task.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getImage() != null) {
            task.setImage(taskDTO.getImage());
        }
        if (taskDTO.getDeadline() != null) {
            task.setDeadline(taskDTO.getDeadline());
        }
        if (taskDTO.getAssignedUserId() != null) {
            task.setAssignedUserId(taskDTO.getAssignedUserId());
        }
        if (taskDTO.getTags() != null) {
            task.setTags(taskDTO.getTags());
        }
        if (taskDTO.getDescription() != null) {
            task.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getStatus() != null) {
            task.setStatus(taskDTO.getStatus());
        }
    }

}
