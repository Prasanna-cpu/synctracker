package com.spring.synctracker.submission_service.repository;

import com.spring.synctracker.submission_service.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {

    @Query("select s from Submission s where s.taskId = ?1")
    List<Submission>  findByTaskId(String taskId);

    @Query("select s from Submission s where s.userId = ?1")
    List<Submission>  findByUserId(String userId);

}
