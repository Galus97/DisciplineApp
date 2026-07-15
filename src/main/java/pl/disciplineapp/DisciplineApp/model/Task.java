package pl.disciplineapp.DisciplineApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @NotBlank
    private String taskName;

    @NotBlank
    private String description;

    private boolean completed;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
