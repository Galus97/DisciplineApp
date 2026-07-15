package pl.disciplineapp.DisciplineApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.disciplineapp.DisciplineApp.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
