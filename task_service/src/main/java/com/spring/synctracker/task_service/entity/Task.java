package com.spring.synctracker.task_service.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.synctracker.task_service.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "assigned_user_id")
    private String assignedUserId;

    @ElementCollection
    @Column(name = "tags")
    @CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
    private List<String> tags = new ArrayList<>();

    @Column(name = "deadline")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private LocalDate deadline;

    @Column(name = "task_created_at", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private LocalDate taskCreatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

}
