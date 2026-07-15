package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.disciplineapp.DisciplineApp.model.Investment;

import java.time.LocalDateTime;
import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findAllByUser_UserId(Long userId);

    @Query("SELECT i FROM Investment i WHERE i.user.userId = :userId AND i.createdAt BETWEEN :from AND :to")
    List<Investment> findAllByUserIdAndCreatedAtBetween(
            @Param("userId") Long userId,
            @Param("from")LocalDateTime from,
            @Param("to") LocalDateTime to);
}
