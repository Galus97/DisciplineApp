package pl.disciplineapp.DisciplineApp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TaskRequest {
    private Long taskId;
    @NotBlank
    private String taskName;
    @NotBlank
    private String description;
    private boolean completed;
    // TODO Those LocalDateTimes should be here?
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime deadline;
    @NotNull
    private Long userId;
}
