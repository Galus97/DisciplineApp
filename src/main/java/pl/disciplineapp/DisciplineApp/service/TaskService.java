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
import pl.disciplineapp.DisciplineApp.util.ServiceValidator;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ServiceValidator serviceValidator;
    private final MessageService messageService;

    public TaskResponse getTaskResponse(Long taskId) {
        serviceValidator.throwIfIdIsNotValid(taskId, ErrorMessages.INVALID_TASK_ID);
        return TaskResponse.fromEntity(getTaskOrThrowIfNotExist(taskId));
    }

    public TaskResponse saveTask(TaskRequest taskRequest) {
        serviceValidator.throwIfRequestIsNull(taskRequest, ErrorMessages.TASK_REQUEST_IS_NULL);
        return TaskResponse.fromEntity(taskRepository.save(buildTask(taskRequest)));
    }

    public void deleteTask(Long taskId) {
        serviceValidator.throwIfIdIsNotValid(taskId, ErrorMessages.INVALID_TASK_ID);
        taskRepository.delete(getTaskOrThrowIfNotExist(taskId));
    }

    public TaskResponse updateTask(TaskRequest taskRequest) {
       // throwIfRequestIsNull(taskRequest);
        Task existingTask = getTaskOrThrowIfNotExist(taskRequest.getTaskId());
        existingTask.setTaskName(taskRequest.getTaskName());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setCompleted(taskRequest.isCompleted());
        existingTask.setCreatedAt(taskRequest.getCreatedAt());
        existingTask.setCompletedAt(taskRequest.getCompletedAt());
        existingTask.setDeadline(taskRequest.getDeadline());

        return TaskResponse.fromEntity(taskRepository.save(existingTask));
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

}
