package com.spring.synctracker.submission_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.synctracker.submission_service.Enum.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String id;

    private String title;

    private String description;

    private String image;

    private TaskStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate taskCreatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate deadline;

    private String assignedUserId;

    private List<String> tags;
}
