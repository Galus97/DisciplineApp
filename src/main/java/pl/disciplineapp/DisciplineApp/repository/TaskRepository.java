package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.disciplineapp.DisciplineApp.model.Task;
import pl.disciplineapp.DisciplineApp.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser(User user, Pageable pageable);

    Optional<Task> findByIdAndUser(Long id, User user);

    @Query("SELECT t FROM Task t WHERE t.user= :user AND t.createdAt BETWEEN :from AND :to")
    List<Task> findAllByUserAndCreatedAtBetween(
            @Param("user") User user,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);
}
