package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.model.Goal;
import pl.disciplineapp.DisciplineApp.model.GoalStep;
import pl.disciplineapp.DisciplineApp.model.User;

import java.util.List;
import java.util.Optional;

public interface GoalStepRepository extends JpaRepository<GoalStep, Long> {
    Optional<GoalStep> findByGoalStepIdAndUser(Long goalStepId, User user);

    List<GoalStep> findAllByGoalAndUser(Goal goal, User user);
}
