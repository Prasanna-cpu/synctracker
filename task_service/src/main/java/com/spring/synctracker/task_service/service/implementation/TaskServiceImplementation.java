package com.spring.synctracker.task_service.service.implementation;

import com.spring.synctracker.task_service.dto.TaskDTO;
import com.spring.synctracker.task_service.entity.Task;
import com.spring.synctracker.task_service.enums.TaskStatus;
import com.spring.synctracker.task_service.exception.BadRequestException;
import com.spring.synctracker.task_service.exception.ForbiddenAuthorizationException;
import com.spring.synctracker.task_service.exception.ObjectNotFoundException;
import com.spring.synctracker.task_service.mapper.TaskMapper;
import com.spring.synctracker.task_service.repository.TaskRepository;
import com.spring.synctracker.task_service.service.abstraction.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, ObjectNotFoundException.class})
@Slf4j
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;


    @Override
    public TaskDTO createTask(TaskDTO taskDTO, String requestRole) {

        if (!"ADMIN".equals(requestRole) && !"ROLE_ADMIN".equals(requestRole)) {
            throw new ForbiddenAuthorizationException("Unauthorized to create task");
        }

        Task task = TaskMapper.mapToTask(taskDTO);
        task.setStatus(TaskStatus.PENDING);
        task.setTaskCreatedAt(LocalDate.now());

        if(task.getTags() == null){
            task.setTags(new ArrayList<>());
        }

        Task savedTask = taskRepository.save(task);
        TaskDTO savedTaskDTO = TaskMapper.mapToTaskDTO(savedTask);
        return savedTaskDTO;

    }

    @Override
    public TaskDTO getTaskById(String Id) {

        Task task = taskRepository
                .findById(Id)
                .orElseThrow(() -> new ObjectNotFoundException("Task not found with the id)) : " + Id));

        TaskDTO taskDTO = TaskMapper.mapToTaskDTO(task);
        return taskDTO;

    }

    @Override
    public List<TaskDTO> getAllTasks() {

        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOS = tasks.stream()
                .map(TaskMapper::mapToTaskDTO)
                .toList();
        return taskDTOS;

    }

    @Override
    public List<TaskDTO> getTasksByStatus(TaskStatus status) {

        List<Task> tasks = taskRepository.findAll();
        List<TaskDTO> taskDTOS = tasks.stream()
                .filter(task -> task.getStatus() == status)
                .map(TaskMapper::mapToTaskDTO)
                .toList();
        return taskDTOS;
    }

    @Override
    public TaskDTO updateTask(String Id, TaskDTO updatedTask) {

        Task task = taskRepository
                .findById(Id)
                .orElseThrow(() -> new ObjectNotFoundException("Task not found with the id : " + Id));
        TaskMapper.updateTaskFromDTO(task, updatedTask);
        Task savedTask =  taskRepository.save(task);
        TaskDTO savedTaskDTO = TaskMapper.mapToTaskDTO(savedTask);
        return savedTaskDTO;

    }

    @Override
    public void deleteTask(String Id) {
        taskRepository.findById(Id)
                .ifPresentOrElse(
                        taskRepository::delete,
                        () -> {
                            throw new ObjectNotFoundException("Task not found with the id : " + Id);
                        }
                );
    }

    @Override
    public TaskDTO assignTaskToUser(String userId, String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ObjectNotFoundException("Task not found with the id : " + taskId));
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.ASSIGNED);
        Task savedTask = taskRepository.save(task);
        TaskDTO savedTaskDTO = TaskMapper.mapToTaskDTO(savedTask);
        return savedTaskDTO;
    }

    @Override
    public List<TaskDTO> assignedUsersTask(String userId, TaskStatus status) {

        List<Task> tasks = taskRepository.findAllByAssignedUserId(userId);
        List<TaskDTO> taskDTOS = tasks.stream()
                .filter(task -> task.getStatus() == status)
                .map(TaskMapper::mapToTaskDTO)
                .toList();
        return taskDTOS;

    }

    @Override
    public TaskDTO completeTask(String taskId, String userId) {

        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new ObjectNotFoundException("Task not found with the id : " + taskId));

        if(task.getAssignedUserId() == null){
            throw new BadRequestException("Task must be assigned to a user");
        }

        if(!task.getAssignedUserId().equals(userId)){
            throw new ForbiddenAuthorizationException("Unauthorized to complete this task");
        }

        if(task.getStatus().equals(TaskStatus.DONE)){
            throw new BadRequestException("Task is already completed");
        }

        task.setStatus(TaskStatus.DONE);
        Task savedTask = taskRepository.save(task);
        TaskDTO savedTaskDTO = TaskMapper.mapToTaskDTO(savedTask);
        return savedTaskDTO;

    }
}
