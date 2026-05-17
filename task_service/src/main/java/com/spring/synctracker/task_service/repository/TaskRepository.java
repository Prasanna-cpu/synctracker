package com.spring.synctracker.task_service.repository;


import com.spring.synctracker.task_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    @Query("SELECT t FROM Task t WHERE t.assignedUserId = :userId")
    List<Task> findAllByAssignedUserId(String userId);
}
