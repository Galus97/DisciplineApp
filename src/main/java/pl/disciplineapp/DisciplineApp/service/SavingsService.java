package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.entity.Saving;
import pl.disciplineapp.DisciplineApp.exception.SavingNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.SavingRepository;

@Service
@RequiredArgsConstructor
public class SavingsService {
    private final SavingRepository savingRepository;
    private final MessageService messageService;

    private Saving getSavingOrThrowIfNotExist(Long savingId) {
        return savingRepository.findById(savingId).orElseThrow(
                () -> new SavingNotFoundException(messageService.getMessage(ErrorMessages.SAVING_NOT_FOUND)));
    }

    private void throwIfIdIsNotValid(Long savingId) {
        if (savingId == null || savingId <= 0) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_SAVING_ID));
        }
    }
}
