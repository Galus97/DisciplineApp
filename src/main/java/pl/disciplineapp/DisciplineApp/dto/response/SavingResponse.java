package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.model.Saving;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record SavingResponse(Long savingId, String savingType, Float TotalValue,
                             Float quantity, Float unitPrice, LocalDateTime createdAt, Long userId) {
    public static SavingResponse fromEntity(Saving saving) {
        return new SavingResponse(
                saving.getSavingId(),
                saving.getSavingType(),
                saving.getTotalValue(),
                saving.getQuantity(),
                saving.getUnitPrice(),
                saving.getCreatedAt(),
                saving.getUser() != null ? saving.getUser().getUserId() : null
        );
    }

    public static List<SavingResponse> fromEntityList(List<Saving> savingList) {
        return savingList.stream()
                .map(SavingResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
