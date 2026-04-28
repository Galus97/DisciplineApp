package pl.disciplineapp.DisciplineApp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.entity.User;
import pl.disciplineapp.DisciplineApp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisterValidator {
    private final UserRepository userRepository;
    private final MessageService messageService;

    public List<String> validateUser(User user) {
        List<String> errors = new ArrayList<>();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.add(messageService.getMessage(ErrorMessages.EMAIL_IS_ALREADY_USED));
        }
        return errors;
    }
}
