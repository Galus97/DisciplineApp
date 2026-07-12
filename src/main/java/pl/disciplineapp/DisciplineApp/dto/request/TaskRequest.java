package pl.disciplineapp.DisciplineApp.dto.request;

import lombok.Builder;
import lombok.Getter;
import pl.disciplineapp.DisciplineApp.entity.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class TaskRequest {
    private Long taskId;
    private String taskName;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime deadline;
    private Long userId;
}
