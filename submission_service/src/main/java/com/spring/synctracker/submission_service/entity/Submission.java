package com.spring.synctracker.submission_service.entity;


import com.spring.synctracker.submission_service.Enum.SubmissionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "submissions")
public class Submission extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "task_id", nullable = false)
    private String taskId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "github_link", nullable = false)
    private String gitHubLink;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status = SubmissionStatus.PENDING;

    @Column(name = "submission_time", nullable = false, updatable = false)
    private LocalDateTime submissionTime;

}
