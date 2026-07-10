package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Task;
import pl.disciplineapp.DisciplineApp.entity.User;

import java.time.LocalDateTime;

public record TaskResponse(Long taskId, String taskName, String description, boolean completed, LocalDateTime createdAt,
                           LocalDateTime completedAt, LocalDateTime deadline, User user) {

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTaskName(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getCompletedAt(),
                task.getDeadline(),
                task.getUser()
        );
    }
}
