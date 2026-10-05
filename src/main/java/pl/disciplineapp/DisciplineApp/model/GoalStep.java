package pl.disciplineapp.DisciplineApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@SQLDelete(sql = "UPDATE goal_steps SET is_deleted = true WHERE goal_step_id = ?")
@SQLRestriction("is_deleted = false")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goal_steps")
public class GoalStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_step_id")
    private Long goalStepId;

    @NotBlank
    private String title;

    private String description;

    @CreationTimestamp
    private LocalDateTime deadline;

    @OneToMany
    @Column(name = "main_goal_id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;
}
