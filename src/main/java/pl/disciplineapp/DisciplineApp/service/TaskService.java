package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.TaskRequest;
import pl.disciplineapp.DisciplineApp.dto.response.TaskResponse;
import pl.disciplineapp.DisciplineApp.entity.Task;
import pl.disciplineapp.DisciplineApp.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final MessageService messageService;

    public TaskResponse getTaskResponse(Long taskId) {
        throwIfIdIsNotValid(taskId);
        return TaskResponse.fromEntity(taskRepository.findById(taskId).orElseThrow());
    }
    

    private Task buildTask(TaskRequest taskRequest) {
        return Task.builder()
                .taskName(taskRequest.getTaskName())
                .description(taskRequest.getDescription())
                .completed(taskRequest.isCompleted())
                .createdAt(taskRequest.getCreatedAt())
                .completedAt(taskRequest.getCompletedAt())
                .deadline(taskRequest.getDeadline())
                .build();
    }

    private void throwIfRequestIsNull(TaskRequest taskRequest) {
        if (taskRequest == null) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.TASK_REQUEST_IS_NULL));
        }
    }


    private void throwIfIdIsNotValid(Long taskId) {
        if (taskId == null || taskId < 1) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_TASK_ID));
        }
    }
}
