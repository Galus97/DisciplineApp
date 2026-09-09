package pl.disciplineapp.DisciplineApp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskRequest {
    private Long taskId;

    @NotBlank
    private String taskName;

    private String description;

    private boolean completed;

    @NotBlank
    private String createdAt;

    @NotBlank
    private String completedAt;

    @NotBlank
    private String deadline;
}
