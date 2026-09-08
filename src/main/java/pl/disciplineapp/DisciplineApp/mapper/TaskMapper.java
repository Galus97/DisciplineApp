package pl.disciplineapp.DisciplineApp.mapper;

import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.request.TaskRequest;
import pl.disciplineapp.DisciplineApp.dto.response.TaskResponse;
import pl.disciplineapp.DisciplineApp.model.Task;
import pl.disciplineapp.DisciplineApp.model.User;

@Component
public class TaskMapper {
    public static TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTaskName(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getCompletedAt(),
                task.getDeadline(),
                task.getUser().getUserId()
        );
    }

    public static Task toTaskModel(TaskRequest taskRequest, User user) {
        return Task.builder()
                .taskId(taskRequest.getTaskId())
                .taskName(taskRequest.getTaskName())
                .description(taskRequest.getDescription())
                .completed(taskRequest.isCompleted())
                .createdAt(taskRequest.getCreatedAt())
                .completedAt(taskRequest.getCompletedAt())
                .deadline(taskRequest.getDeadline())
                .user(user)
                .build();
    }
}
