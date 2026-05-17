package com.spring.synctracker.submission_service.mapper;

import com.spring.synctracker.submission_service.dto.SubmissionDTO;
import com.spring.synctracker.submission_service.entity.Submission;

import java.time.LocalDateTime;

public class SubmissionMapper {

    public static SubmissionDTO mapToSubmissionDTO(Submission submission) {

        SubmissionDTO submissionDTO = new SubmissionDTO();

        submissionDTO.setId(submission.getId());
        submissionDTO.setTaskId(submission.getTaskId());
        submissionDTO.setUserId(submission.getUserId());
        submissionDTO.setStatus(submission.getStatus());
        submissionDTO.setGitHubLink(submission.getGitHubLink());
        submissionDTO.setSubmissionTime(submission.getSubmissionTime());

        return submissionDTO;

    }

    public static Submission mapToSubmission(SubmissionDTO submissionDTO) {

        Submission submission = new Submission();

        submission.setUserId(submissionDTO.getUserId());
        submission.setTaskId(submissionDTO.getTaskId());
        submission.setGitHubLink(submissionDTO.getGitHubLink());
        submission.setStatus(submissionDTO.getStatus());
        submission.setSubmissionTime(LocalDateTime.now());

        return submission;

    }

    public static void updateSubmissionFromDTO(Submission submission, SubmissionDTO submissionDTO) {

        if(submissionDTO.getTaskId() != null){
            submission.setTaskId(submissionDTO.getTaskId());
        }

        if(submissionDTO.getUserId() != null){
            submission.setUserId(submissionDTO.getUserId());
        }

        if(submissionDTO.getGitHubLink() != null){
            submission.setGitHubLink(submissionDTO.getGitHubLink());
        }

        if(submissionDTO.getStatus() != null){
            submission.setStatus(submissionDTO.getStatus());
        }

//        if(submissionDTO.getSubmissionTime() != null){
//            submission.setSubmissionTime(submissionDTO.getSubmissionTime());
//        }

    }

}
