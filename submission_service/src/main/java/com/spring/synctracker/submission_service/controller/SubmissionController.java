package com.spring.synctracker.submission_service.controller;

import com.spring.synctracker.submission_service.Enum.SubmissionStatus;
import com.spring.synctracker.submission_service.dto.SubmissionDTO;
import com.spring.synctracker.submission_service.entity.Submission;
import com.spring.synctracker.submission_service.response.ApiResponse;
import com.spring.synctracker.submission_service.serializer.UserSerializer;
import com.spring.synctracker.submission_service.service.abstraction.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;
    private final UserSerializer userSerializer;

    @GetMapping("/")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Submission Service");
    }

    @PostMapping("/submit-task/{taskId}/user/{userId}")
    public ResponseEntity<ApiResponse> submitTaskHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String userId,
            @PathVariable String taskId,
            @RequestParam("githubLink") String githubLink
    ){
        userSerializer.serializeUser(jwt);
        SubmissionDTO submissionDTO = submissionService.submitTask(taskId, userId, githubLink, jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(
                        "Task submitted successfully",
                        submissionDTO,
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED
                )
        );
    }

    @GetMapping("/all-submissions/task/{taskId}")
    public ResponseEntity<ApiResponse> getAllTaskSubmissionsHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String taskId
    ){
        List<SubmissionDTO> submissionDTOS = submissionService.getAllTaskSubmissions(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Submissions of the given task retrieved successfully",
                        submissionDTOS,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("/accept-decline-submission/{submissionId}")
    public ResponseEntity<ApiResponse> acceptDeclineSubmissionHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String submissionId,
            @RequestParam("status") SubmissionStatus status
    ){
        SubmissionDTO submissionDTO = submissionService.acceptDeclineSubmission(submissionId, status, jwt);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Submission accepted successfully",
                        submissionDTO,
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }
}