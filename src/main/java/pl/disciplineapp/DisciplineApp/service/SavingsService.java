package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.SavingRequest;
import pl.disciplineapp.DisciplineApp.dto.response.SavingResponse;
import pl.disciplineapp.DisciplineApp.entity.Saving;
import pl.disciplineapp.DisciplineApp.exception.SavingNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.SavingRepository;

@Service
@RequiredArgsConstructor
public class SavingsService {
    private final SavingRepository savingRepository;
    private final MessageService messageService;

    public SavingResponse getSavingResponse(Long savingId) {
        throwIfIdIsNotValid(savingId);
        return SavingResponse.fromEntity(getSavingOrThrowIfNotExist(savingId));
    }

    public SavingResponse saveSaving(SavingRequest savingRequest) {
        throwIfRequestIsNull(savingRequest);
        return SavingResponse.fromEntity(buildSaving(savingRequest));
    }

    private Saving buildSaving(SavingRequest savingRequest) {
        return Saving.builder()
                .savingType(savingRequest.getSavingType())
                .totalValue(savingRequest.getTotalValue())
                .quantity(savingRequest.getQuantity())
                .unitPrice(savingRequest.getUnitPrice())
                .build();
    }

    private void throwIfRequestIsNull(SavingRequest savingRequest) {
        if (savingRequest == null) {
           throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.SAVING_REQUEST_IS_NULL));
        }
    }

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
