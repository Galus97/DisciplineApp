package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser_UserId(Long userId);
}
