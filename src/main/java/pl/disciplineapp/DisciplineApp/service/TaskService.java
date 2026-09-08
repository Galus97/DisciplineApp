package pl.disciplineapp.DisciplineApp.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.TaskRequest;
import pl.disciplineapp.DisciplineApp.dto.response.TaskResponse;
import pl.disciplineapp.DisciplineApp.mapper.TaskMapper;
import pl.disciplineapp.DisciplineApp.model.Task;
import pl.disciplineapp.DisciplineApp.exception.TaskNotFoundException;
import pl.disciplineapp.DisciplineApp.model.User;
import pl.disciplineapp.DisciplineApp.repository.TaskRepository;
import pl.disciplineapp.DisciplineApp.util.ServiceValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ServiceValidator serviceValidator;
    private final MessageService messageService;

    @Transactional(readOnly = true)
    public TaskResponse getTaskResponse(Long taskId) {
        serviceValidator.throwIfIdIsNotValid(taskId, ErrorMessages.INVALID_TASK_ID);
        return TaskMapper.toTaskResponse(getTaskOrThrowIfNotExist(taskId));
    }

    @Transactional
    public TaskResponse saveTask(TaskRequest taskRequest, User user) {
        serviceValidator.throwIfRequestIsNull(taskRequest, ErrorMessages.TASK_REQUEST_IS_NULL);
        Task task = TaskMapper.toTaskModel(taskRequest, user);
        return TaskMapper.toTaskResponse(taskRepository.save(task));
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

        return TaskMapper.toTaskResponse(taskRepository.save(existingTask));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTask(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return taskRepository.findAllByUser_UserId(userId)
                .stream()
                .map(TaskMapper::toTaskResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksBetweenDates(Long userId, String from, String to) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        //This throws exception if user doesn't exist
        userService.getUserOrThrowIfNotExist(userId);

        if (from == null || to == null) {
           throw new IllegalArgumentException(ErrorMessages.INVALID_PARAMS);
        }

        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(from);
            LocalDateTime toDateTime = LocalDateTime.parse(to);
            return taskRepository.findAllByUserIdAndCreatedAtBetween(userId, fromDateTime, toDateTime)
                    .stream()
                    .map(TaskMapper::toTaskResponse)
                    .toList();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT_PARAMS);
        }
    }

    private Task getTaskOrThrowIfNotExist(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(messageService.getMessage(ErrorMessages.TASK_NOT_FOUND, taskId)));
    }
}
