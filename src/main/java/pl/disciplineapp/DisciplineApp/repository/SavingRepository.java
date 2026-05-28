package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.entity.Saving;

public interface SavingRepository extends JpaRepository<Saving, Long> {
}
