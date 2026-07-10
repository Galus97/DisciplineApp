package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.entity.Saving;

import java.util.List;

public interface SavingRepository extends JpaRepository<Saving, Long> {

    List<Saving> findAllByUser_UserId(Long userId);
}
