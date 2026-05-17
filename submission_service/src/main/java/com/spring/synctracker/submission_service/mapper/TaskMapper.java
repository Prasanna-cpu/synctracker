package com.spring.synctracker.submission_service.mapper;

import com.spring.synctracker.submission_service.Enum.TaskStatus;
import com.spring.synctracker.submission_service.dto.TaskDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class TaskMapper {

    private static final DateTimeFormatter TASK_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public static TaskDTO convertToTaskDTO(Map<String,Object> data){
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(data.get("id")!=null ? (String) data.get("id") : null);
        taskDTO.setTitle(data.get("title")!=null ? (String) data.get("title") : null);
        taskDTO.setDescription(data.get("description")!=null ? (String) data.get("description") : null);
        taskDTO.setImage(data.get("image")!=null ? (String) data.get("image") : null);
//        taskDTO.setStatus(data.get("taskStatus")!=null ? TaskStatus.valueOf(String.valueOf(data.get("taskStatus"))) : null);
        if(data.get("status") != null){
            taskDTO.setStatus(data.get("status") != null ? TaskStatus.valueOf(String.valueOf(data.get("status"))) : null);
        }
        taskDTO.setTaskCreatedAt(parseTaskDate(data.get("taskCreatedAt")));
        taskDTO.setDeadline(parseTaskDate(data.get("deadline")));
        taskDTO.setAssignedUserId(data.get("assignedUserId")!=null ? (String) data.get("assignedUserId") : null);
        taskDTO.setTags(data.get("tags")!=null ? (List<String>) data.get("tags") : null);
        return taskDTO;

    }

    private static LocalDate parseTaskDate(Object value) {
        return value != null ? LocalDate.parse(String.valueOf(value), TASK_DATE_FORMATTER) : null;
    }

}
