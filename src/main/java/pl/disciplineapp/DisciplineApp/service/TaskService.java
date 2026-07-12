package pl.disciplineapp.DisciplineApp.service;

import jakarta.transaction.Transactional;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ServiceValidator serviceValidator;
    private final MessageService messageService;

    public TaskResponse getTaskResponse(Long taskId) {
        serviceValidator.throwIfIdIsNotValid(taskId, ErrorMessages.INVALID_TASK_ID);
        return TaskResponse.fromEntity(getTaskOrThrowIfNotExist(taskId));
    }

    @Transactional
    public TaskResponse saveTask(TaskRequest taskRequest) {
        serviceValidator.throwIfRequestIsNull(taskRequest, ErrorMessages.TASK_REQUEST_IS_NULL);
        return TaskResponse.fromEntity(taskRepository.save(buildTask(taskRequest)));
    }

    @Transactional
    public void deleteTask(Long taskId) {
        serviceValidator.throwIfIdIsNotValid(taskId, ErrorMessages.INVALID_TASK_ID);
        taskRepository.delete(getTaskOrThrowIfNotExist(taskId));
    }

    @Transactional
    public TaskResponse updateTask(TaskRequest taskRequest) {
        serviceValidator.throwIfRequestIsNull(taskRequest, ErrorMessages.TASK_REQUEST_IS_NULL);
        Task existingTask = getTaskOrThrowIfNotExist(taskRequest.getTaskId());
        existingTask.setTaskName(taskRequest.getTaskName());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setCompleted(taskRequest.isCompleted());
        existingTask.setCreatedAt(taskRequest.getCreatedAt());
        existingTask.setCompletedAt(taskRequest.getCompletedAt());
        existingTask.setDeadline(taskRequest.getDeadline());
        existingTask.setUser(userService.getUserOrThrowIfNotExist(taskRequest.getUserId()));

        return TaskResponse.fromEntity(taskRepository.save(existingTask));
    }

    public List<TaskResponse> getAllTaskResponseByUser(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return TaskResponse.fromEntityList(taskRepository.findAllByUser_UserId(userId));
    }

    private Task buildTask(TaskRequest taskRequest) {
        return Task.builder()
                .taskName(taskRequest.getTaskName())
                .description(taskRequest.getDescription())
                .completed(taskRequest.isCompleted())
                .createdAt(taskRequest.getCreatedAt())
                .completedAt(taskRequest.getCompletedAt())
                .deadline(taskRequest.getDeadline())
                .user(userService.getUserOrThrowIfNotExist(taskRequest.getUserId()))
                .build();
    }

    private Task getTaskOrThrowIfNotExist(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(messageService.getMessage(ErrorMessages.TASK_NOT_FOUND)));
    }

}
