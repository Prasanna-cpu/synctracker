package com.spring.synctracker.submission_service.service.abstraction;

import com.spring.synctracker.submission_service.Enum.SubmissionStatus;
import com.spring.synctracker.submission_service.dto.SubmissionDTO;
import com.spring.synctracker.submission_service.entity.Submission;

import java.util.List;

public interface SubmissionService {

    SubmissionDTO submitTask(String taskId, String userId, String gitHubLink, String jwt);

    SubmissionDTO getTaskSubmissionById(String submissionId);

    List<SubmissionDTO> getAllTaskSubmissions(String taskId);

    List<SubmissionDTO> getTaskSubmissionsByTaskId(String taskId);

    SubmissionDTO acceptDeclineSubmission(String submissionId, SubmissionStatus status, String jwt);

}
