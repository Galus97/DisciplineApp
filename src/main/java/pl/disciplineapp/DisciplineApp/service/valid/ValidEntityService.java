package pl.disciplineapp.DisciplineApp.service.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.repository.valid.ValidEntity;

@Service
@RequiredArgsConstructor
public class ValidEntityService implements ValidEntity {
    private final MessageService messageService;
    @Override
    public void throwIfIdIsNotValid(Long id, String errorMessage) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException(messageService.getMessage(errorMessage));
        }

    }

    @Override
    public void throwIfRequestIsNull(Object request) {

    }

    @Override
    public Object getEntityOrThrowIfNotExist(Long id) {
        return null;
    }
}
