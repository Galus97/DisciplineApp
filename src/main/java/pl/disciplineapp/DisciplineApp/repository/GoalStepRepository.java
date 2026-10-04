package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.model.GoalStep;

public interface GoalStepRepository extends JpaRepository<GoalStep, Long> {
}
