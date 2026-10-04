package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {

}
