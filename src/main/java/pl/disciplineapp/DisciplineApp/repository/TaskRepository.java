package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
