package pl.disciplineapp.DisciplineApp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
}
