package pl.disciplineapp.DisciplineApp.mapper;

import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.response.TaskResponse;
import pl.disciplineapp.DisciplineApp.model.Task;

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
}
