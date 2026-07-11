package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Task;
import pl.disciplineapp.DisciplineApp.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static List<TaskResponse> fromEntityList(List<Task> taskList) {
        List<TaskResponse> taskResponseList = new ArrayList<>();
        if (!taskList.isEmpty()) {
            for (Task task : taskList) {
                taskResponseList.add(fromEntity(task));
            }
        }
        return taskResponseList;
    }
}
