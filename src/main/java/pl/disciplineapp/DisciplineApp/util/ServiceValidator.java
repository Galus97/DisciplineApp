package pl.disciplineapp.DisciplineApp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;

@Component
@RequiredArgsConstructor
public class ServiceValidator {
    private final MessageService messageService;

    public void throwIfRequestIsNull(Object request, String errorMessageKey) {
        if (request == null) {
            throw new IllegalArgumentException(messageService.getMessage(errorMessageKey));
        }
    }

    public void throwIfIdIsNotValid(Long id, String errorMessageKey) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException(messageService.getMessage(errorMessageKey, id));
        }
    }

    public void throwIfEmailAlreadyExist(String email) {
        if (email == null || email.isBlank()) {
           throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.EMAIL_IS_INVALID, email));
        }
    }
}
