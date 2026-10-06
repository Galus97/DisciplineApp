package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.disciplineapp.DisciplineApp.model.Goal;
import pl.disciplineapp.DisciplineApp.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByUser(User user);

    Optional<Goal> findByGoalIdAndUser(Long goalId, User user);

    @Query("SELECT g FROM goals g WHERE g.user = :user AND g.deadline <= :deadline")
    List<Goal> findAllByUserAndDeadlineBefore(
            @Param("user") User user,
            @Param("deadline") LocalDateTime deadline
            );
}
