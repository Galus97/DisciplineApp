package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.disciplineapp.DisciplineApp.model.Saving;

import java.time.LocalDateTime;
import java.util.List;

public interface SavingRepository extends JpaRepository<Saving, Long> {

    List<Saving> findAllByUser_UserId(Long userId);

    @Query("SELECT s FROM Saving s WHERE s.user.userId = :userId AND s.createdAt BETWEEN :from AND :to")
    List<Saving> findAllByUserIdAndCreatedAtBetween(
            @Param("userId") Long userId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);
}