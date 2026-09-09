package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.disciplineapp.DisciplineApp.model.Saving;
import pl.disciplineapp.DisciplineApp.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface SavingRepository extends JpaRepository<Saving, Long> {

    List<Saving> findAllByUser(User user, Pageable pageable);

    @Query("SELECT s FROM Saving s WHERE s.user = :user AND s.createdAt BETWEEN :from AND :to")
    List<Saving> findAllByUserIdAndCreatedAtBetween(
            @Param("user") User user,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);
}