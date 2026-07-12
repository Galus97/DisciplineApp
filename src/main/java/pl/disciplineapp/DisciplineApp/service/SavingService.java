package pl.disciplineapp.DisciplineApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.SavingRequest;
import pl.disciplineapp.DisciplineApp.dto.response.SavingResponse;
import pl.disciplineapp.DisciplineApp.entity.Saving;
import pl.disciplineapp.DisciplineApp.exception.SavingNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.SavingRepository;
import pl.disciplineapp.DisciplineApp.util.ServiceValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingService {
    private final SavingRepository savingRepository;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;

    public SavingResponse getSavingResponse(Long savingId) {
        serviceValidator.throwIfIdIsNotValid(savingId, ErrorMessages.INVALID_SAVING_ID);
        return SavingResponse.fromEntity(getSavingOrThrowIfNotExist(savingId));
    }

    @Transactional
    public SavingResponse saveSaving(SavingRequest savingRequest) {
        serviceValidator.throwIfRequestIsNull(savingRequest, ErrorMessages.SAVING_REQUEST_IS_NULL);
        return SavingResponse.fromEntity(savingRepository.save(buildSaving(savingRequest)));
    }

    @Transactional
    public void deleteSaving(Long savingId) {
        serviceValidator.throwIfIdIsNotValid(savingId, ErrorMessages.INVALID_SAVING_ID);
        savingRepository.delete(getSavingOrThrowIfNotExist(savingId));
    }

    @Transactional
    public SavingResponse updateSaving(SavingRequest savingRequest) {
        serviceValidator.throwIfRequestIsNull(savingRequest, ErrorMessages.SAVING_REQUEST_IS_NULL);
        serviceValidator.throwIfIdIsNotValid(savingRequest.getSavingId(), ErrorMessages.INVALID_SAVING_ID);

        Saving existingSaving = getSavingOrThrowIfNotExist(savingRequest.getSavingId());
        existingSaving.setSavingType(savingRequest.getSavingType());
        existingSaving.setTotalValue(savingRequest.getTotalValue());
        existingSaving.setQuantity(savingRequest.getQuantity());
        existingSaving.setUnitPrice(savingRequest.getUnitPrice());

        return SavingResponse.fromEntity(savingRepository.save(existingSaving));
    }

    public List<SavingResponse> getAllSavingResponseByUser(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return SavingResponse.fromEntityList(savingRepository.findAllByUser_UserId(userId));
    }

    private Saving buildSaving(SavingRequest savingRequest) {
        return Saving.builder()
                .savingType(savingRequest.getSavingType())
                .totalValue(savingRequest.getTotalValue())
                .quantity(savingRequest.getQuantity())
                .unitPrice(savingRequest.getUnitPrice())
                .user(savingRequest.getUser())
                .build();
    }

    private Saving getSavingOrThrowIfNotExist(Long savingId) {
        return savingRepository.findById(savingId).orElseThrow(
                () -> new SavingNotFoundException(messageService.getMessage(ErrorMessages.SAVING_NOT_FOUND)));
    }
}
