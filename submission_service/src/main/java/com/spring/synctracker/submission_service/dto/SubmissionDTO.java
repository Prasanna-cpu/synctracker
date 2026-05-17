package com.spring.synctracker.submission_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.synctracker.submission_service.Enum.SubmissionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmissionDTO {

    private String id;

    @NotBlank(message = "Task id cannot be blank")
    private String taskId;

    @NotBlank(message = "User id cannot be blank")
    private String userId;

    @NotBlank(message = "GitHub link cannot be blank")
    private String gitHubLink;

    @NotBlank(message = "Status cannot be null")
    private SubmissionStatus status;

    @NotNull(message = "Submission time cannot be null")
    private LocalDateTime submissionTime;

}
