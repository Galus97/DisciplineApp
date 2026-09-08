package pl.disciplineapp.DisciplineApp.dto.response;

import java.time.LocalDateTime;

public record InvestmentResponse(
        Long investmentId,
        String investmentType,
        Float totalValue,
        Float quantity,
        Float unitPrice,
        LocalDateTime createdAt,
        Long userId) {
}
