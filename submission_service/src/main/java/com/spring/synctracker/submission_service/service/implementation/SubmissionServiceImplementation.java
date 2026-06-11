package com.spring.synctracker.submission_service.service.implementation;

import com.spring.synctracker.submission_service.Enum.SubmissionStatus;
import com.spring.synctracker.submission_service.dto.SubmissionDTO;
import com.spring.synctracker.submission_service.dto.TaskDTO;
import com.spring.synctracker.submission_service.entity.Submission;
import com.spring.synctracker.submission_service.exception.ObjectNotFoundException;
import com.spring.synctracker.submission_service.exception.ServiceUnavailableException;
import com.spring.synctracker.submission_service.mapper.SubmissionMapper;
import com.spring.synctracker.submission_service.repository.SubmissionRepository;
import com.spring.synctracker.submission_service.serializer.TaskSerializer;
import com.spring.synctracker.submission_service.serializer.UserSerializer;
import com.spring.synctracker.submission_service.service.abstraction.SubmissionService;
import com.spring.synctracker.submission_service.service.abstraction.TaskService;
import com.spring.synctracker.submission_service.service.abstraction.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, ObjectNotFoundException.class})
@Slf4j
public class SubmissionServiceImplementation implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final TaskSerializer taskSerializer;
    private final UserSerializer userSerializer;

    public SubmissionDTO submitTaskFallback(String taskId, String userId, String gitHubLink, String jwt, Throwable throwable) {
        log.error("Failed to submit task with id {} for user with id {}. Error: {}", taskId, userId, throwable.getMessage());
        throw new ServiceUnavailableException("Task Service down , please try again later.");
    }

    public SubmissionDTO acceptDeclineSubmissionFallback(String submissionId, SubmissionStatus status, String jwt, Throwable throwable) {
        log.error("Failed to accept/decline submission with id {} for user with id {}. Error: {}", submissionId, jwt, throwable.getMessage());
        throw new ServiceUnavailableException("Task Service down , please try again later.");
    }

    @CircuitBreaker(name = "taskService", fallbackMethod = "submitTaskFallback")
    @Retry(name = "taskService")
    @Override
    public SubmissionDTO submitTask(String taskId, String userId, String gitHubLink, String jwt) {

        TaskDTO taskDTO = taskSerializer.serializeTask(jwt, taskId);

        if(taskDTO != null){
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGitHubLink(gitHubLink);
            submission.setStatus(SubmissionStatus.PENDING);
            submission.setSubmissionTime(LocalDateTime.now());
            Submission savedSubmission = submissionRepository.save(submission);
            SubmissionDTO savedSubmissionDTO = SubmissionMapper.mapToSubmissionDTO(savedSubmission);
            return savedSubmissionDTO;
        }

        else{
            throw new ObjectNotFoundException("Task not found");
        }

    }

    @Override
    public SubmissionDTO getTaskSubmissionById(String submissionId) {

        Submission submission = submissionRepository
                .findById(submissionId)
                .orElseThrow(() -> new ObjectNotFoundException("Submission not found"));
        SubmissionDTO submissionDTO = SubmissionMapper.mapToSubmissionDTO(submission);
        return submissionDTO;

    }

    @Override
    public List<SubmissionDTO> getAllTaskSubmissions(String taskId) {
        List<Submission> submissions = submissionRepository
                .findByTaskId(taskId);
        List<SubmissionDTO> submissionDTOS = submissions
                .stream()
                .map(SubmissionMapper::mapToSubmissionDTO)
                .toList();
        return submissionDTOS;
    }

    @Override
    public List<SubmissionDTO> getTaskSubmissionsByTaskId(String taskId) {
        List<Submission> submissions = submissionRepository
                .getSubmissionByTaskId(taskId);
        List<SubmissionDTO> submissionDTOS = submissions
                .stream()
                .map(SubmissionMapper::mapToSubmissionDTO)
                .toList();
        return submissionDTOS;
    }

    @CircuitBreaker(name = "taskService", fallbackMethod = "acceptDeclineSubmissionFallback")
    @Retry(name = "taskService")
    @Override
    public SubmissionDTO acceptDeclineSubmission(String submissionId, SubmissionStatus status, String jwt) {
        Submission submission = submissionRepository
                .findById(submissionId)
                .orElseThrow(() -> new ObjectNotFoundException("Submission not found with the id : " + submissionId));
        SubmissionStatus previousStatus = submission.getStatus();
        submission.setStatus(status);
        if(previousStatus != SubmissionStatus.ACCEPTED && status == SubmissionStatus.ACCEPTED){
            taskSerializer.serializeCompletedTask(submission.getTaskId(), submission.getUserId(), jwt);
        }
        Submission savedSubmission = submissionRepository.save(submission);
        SubmissionDTO savedSubmissionDTO = SubmissionMapper.mapToSubmissionDTO(savedSubmission);
        return savedSubmissionDTO;
    }
}
