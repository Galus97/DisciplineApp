package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.disciplineapp.DisciplineApp.model.Investment;
import pl.disciplineapp.DisciplineApp.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findAllByUser(User user);

    Optional<Investment> findByInvestmentIdAndUser(Long investmentId, User user);

    @Query("SELECT i FROM Investment i WHERE i.user = :user AND i.createdAt BETWEEN :from AND :to")
    List<Investment> findAllByUserIdAndCreatedAtBetween(
            @Param("user") User user,
            @Param("from")LocalDateTime from,
            @Param("to") LocalDateTime to);
}
