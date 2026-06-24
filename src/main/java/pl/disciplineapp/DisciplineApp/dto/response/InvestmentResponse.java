package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Investment;

public record InvestmentResponse(Long investmentId, String investmentType, Float totalValue,
                                 Float quantity, Float unitPrice) {

    public static InvestmentResponse fromEntity(Investment investment) {
        return new InvestmentResponse(
                investment.getInvestmentId(),
                investment.getInvestmentType(),
                investment.getTotalValue(),
                investment.getQuantity(),
                investment.getUnitPrice()
        );
    }
}
