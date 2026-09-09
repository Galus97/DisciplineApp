package pl.disciplineapp.DisciplineApp.service;

import org.springframework.data.domain.Pageable;
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
    private final ServiceValidator serviceValidator;
    private final MessageService messageService;

    @Transactional(readOnly = true)
    public TaskResponse getTaskResponse(Long taskId, User user) {
        serviceValidator.throwIfIdIsNotValid(taskId, ErrorMessages.INVALID_TASK_ID);
        return TaskMapper.toTaskResponse(getTaskOrThrowIfNotExist(taskId, user));
    }

    @Transactional
    public TaskResponse saveTask(TaskRequest taskRequest, User user) {
        serviceValidator.throwIfRequestIsNull(taskRequest, ErrorMessages.TASK_REQUEST_IS_NULL);
        Task task = TaskMapper.toTaskModel(taskRequest, user);
        return TaskMapper.toTaskResponse(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long taskId, User user) {
        serviceValidator.throwIfIdIsNotValid(taskId, ErrorMessages.INVALID_TASK_ID);
        taskRepository.delete(getTaskOrThrowIfNotExist(taskId, user));
    }

    @Transactional
    public TaskResponse updateTask(TaskRequest taskRequest, User user) {
        serviceValidator.throwIfRequestIsNull(taskRequest, ErrorMessages.TASK_REQUEST_IS_NULL);

        Task existingTask = getTaskOrThrowIfNotExist(taskRequest.getTaskId(), user);
        existingTask.setTaskName(taskRequest.getTaskName());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setCompleted(taskRequest.isCompleted());
        existingTask.setCreatedAt(LocalDateTime.parse(taskRequest.getCreatedAt()));
        existingTask.setCompletedAt(LocalDateTime.parse(taskRequest.getCompletedAt()));
        existingTask.setDeadline(LocalDateTime.parse(taskRequest.getDeadline()));
        existingTask.setUser(user);

        return TaskMapper.toTaskResponse(taskRepository.save(existingTask));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTask(User user, Pageable pageable) {
        serviceValidator.throwIfIdIsNotValid(user.getUserId(), ErrorMessages.INVALID_USER_ID);
        return taskRepository.findAllByUser(user, pageable)
                .stream()
                .map(TaskMapper::toTaskResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksBetweenDates(User user, String from, String to, Pageable pageable) {
        serviceValidator.throwIfIdIsNotValid(user.getUserId(), ErrorMessages.INVALID_USER_ID);

        if (from == null || to == null) {
           throw new IllegalArgumentException(ErrorMessages.INVALID_PARAMS);
        }

        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(from);
            LocalDateTime toDateTime = LocalDateTime.parse(to);
            return taskRepository.findAllByUserAndCreatedAtBetween(user, fromDateTime, toDateTime, pageable)
                    .stream()
                    .map(TaskMapper::toTaskResponse)
                    .toList();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT_PARAMS);
        }
    }

    private Task getTaskOrThrowIfNotExist(Long taskId, User user) {
        return taskRepository.findByTaskIdAndUser(taskId, user).orElseThrow(
                () -> new TaskNotFoundException(
                        messageService.getMessage(ErrorMessages.TASK_NOT_FOUND, taskId)));
    }
}
