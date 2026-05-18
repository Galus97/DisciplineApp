package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.TaskRequest;
import pl.disciplineapp.DisciplineApp.dto.response.TaskResponse;
import pl.disciplineapp.DisciplineApp.entity.Task;
import pl.disciplineapp.DisciplineApp.exception.TaskNotFoundException;
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

    public TaskResponse saveTask(TaskRequest taskRequest) {
        throwIfRequestIsNull(taskRequest);
        return TaskResponse.fromEntity(taskRepository.save(buildTask(taskRequest)));
    }

    public void deleteTask(Long taskId) {
        throwIfIdIsNotValid(taskId);
        taskRepository.deleteById(taskId);
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

    private Task getTaskOrThrowIfNotExist(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(messageService.getMessage(ErrorMessages.TASK_NOT_FOUND)));
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
