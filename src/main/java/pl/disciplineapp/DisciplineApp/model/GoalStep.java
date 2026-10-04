package pl.disciplineapp.DisciplineApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goal_steps")
public class GoalStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalStepId;

    @NotBlank
    private String title;

    private String description;
}
