package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.repository.SavingsRepository;

@Service
@RequiredArgsConstructor
public class SavingsService {
    private final SavingsRepository savingsRepository;
    private final MessageService messageService;


    private void throwIfIdIsNotValid(Long savingId) {
        if (savingId == null || savingId <= 0) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_SAVING_ID));
        }
    }
}
