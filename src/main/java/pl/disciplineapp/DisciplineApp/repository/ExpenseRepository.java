package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
