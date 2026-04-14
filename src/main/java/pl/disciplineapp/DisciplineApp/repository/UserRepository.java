package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
