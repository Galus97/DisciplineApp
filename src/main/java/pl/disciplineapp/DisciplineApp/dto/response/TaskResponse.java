package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record TaskResponse(Long taskId, String taskName, String description, boolean completed, LocalDateTime createdAt,
                           LocalDateTime completedAt, LocalDateTime deadline, Long userId) {

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTaskName(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getCompletedAt(),
                task.getDeadline(),
                task.getUser() != null ? task.getUser().getUserId() : null
        );
    }

    public static List<TaskResponse> fromEntityList(List<Task> taskList) {
        return taskList.stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
