package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.response.TaskResponse;
import pl.disciplineapp.DisciplineApp.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final MessageService messageService;

    public TaskResponse getTaskResponse(Long taskId) {
        return TaskResponse.fromEntity(taskRepository.findById(taskId).orElseThrow());
    }
}
