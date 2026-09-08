package pl.disciplineapp.DisciplineApp.dto.response;

import java.time.LocalDateTime;

public record SavingResponse(
        Long savingId,
        String savingType,
        Float TotalValue,
        Float quantity,
        Float unitPrice,
        LocalDateTime createdAt,
        Long userId) {
}
