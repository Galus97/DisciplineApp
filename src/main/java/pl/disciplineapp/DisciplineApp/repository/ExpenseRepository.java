package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.model.Expense;
import pl.disciplineapp.DisciplineApp.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByExpenseIdAndUser(Long expenseId, User user);

    List<Expense> findAllByUser(User user);

    List<Expense> findAllByUserAndCreatedAtBetween(User user, LocalDateTime from, LocalDateTime to);
}
