package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.TaskRequest;
import pl.disciplineapp.DisciplineApp.dto.response.TaskResponse;
import pl.disciplineapp.DisciplineApp.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> showTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskResponse(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse savedTask = taskService.saveTask(taskRequest);
        return ResponseEntity.created(URI.create("/task/" + savedTask.taskId())).body(savedTask);
    }

    @PutMapping
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.updateTask(taskRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponse>> getTasks(
            @PathVariable Long userId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {

        if (from != null && to != null) {
            return ResponseEntity.ok(taskService.getTasksBetweenDates(userId, from, to));
        }
        return ResponseEntity.ok(taskService.getAllTask(userId));
    }
}
