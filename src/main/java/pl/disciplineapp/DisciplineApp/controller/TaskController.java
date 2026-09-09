package pl.disciplineapp.DisciplineApp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.TaskRequest;
import pl.disciplineapp.DisciplineApp.dto.response.TaskResponse;
import pl.disciplineapp.DisciplineApp.model.User;
import pl.disciplineapp.DisciplineApp.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> showTask(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getTaskResponse(id, user));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody @Valid TaskRequest taskRequest,
            @AuthenticationPrincipal User user) {
        TaskResponse savedTask = taskService.saveTask(taskRequest, user);
        return ResponseEntity.created(URI.create("/task/" + savedTask.taskId())).body(savedTask);
    }

    @PutMapping
    public ResponseEntity<TaskResponse> updateTask(
            @RequestBody @Valid TaskRequest taskRequest,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.updateTask(taskRequest, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteTask(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @AuthenticationPrincipal User user,
            Pageable pageable
            ) {

        if (from != null && to != null) {
            return ResponseEntity.ok(taskService.getTasksBetweenDates(user, from, to, pageable));
        }
        return ResponseEntity.ok(taskService.getAllTask(user, pageable));
    }
}
