package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.model.Investment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record InvestmentResponse(Long investmentId, String investmentType, Float totalValue,
                                 Float quantity, Float unitPrice, LocalDateTime createdAt, Long userId) {

    public static InvestmentResponse fromEntity(Investment investment) {
        return new InvestmentResponse(
                investment.getInvestmentId(),
                investment.getInvestmentType(),
                investment.getTotalValue(),
                investment.getQuantity(),
                investment.getUnitPrice(),
                investment.getCreatedAt(),
                investment.getUser() != null ? investment.getUser().getUserId() : null
        );
    }

    public static List<InvestmentResponse> fromEntityList(List<Investment> investmentList) {
        return investmentList.stream()
                .map(InvestmentResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
