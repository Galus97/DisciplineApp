package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.exception.UserNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private void throwIfIdIsNotValid(Long userId) {
        if (userId == null || userId <= 0) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
    }
}
