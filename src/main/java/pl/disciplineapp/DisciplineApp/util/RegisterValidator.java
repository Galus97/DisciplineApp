package pl.disciplineapp.DisciplineApp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.UserRegistrationRequest;
import pl.disciplineapp.DisciplineApp.model.User;
import pl.disciplineapp.DisciplineApp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisterValidator {
    private final UserRepository userRepository;
    private final MessageService messageService;

    public List<String> validateUser(UserRegistrationRequest userRequest) {
        List<String> errors = new ArrayList<>();

        Optional<User> ifUserExistByEmail = userRepository.findByEmail(userRequest.getEmail());
        if (ifUserExistByEmail.isPresent()) {
            errors.add(messageService.getMessage(ErrorMessages.EMAIL_IS_ALREADY_USED));
        }
        if (!userRequest.getPassword().equals(userRequest.getRepeatPassword())) {
            errors.add(messageService.getMessage(ErrorMessages.PASSWORDS_ARE_NOT_EQUALS));
        }

        return errors;
    }
}
