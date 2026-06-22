package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Investment;

public record InvestmentRequest(Long investmentId, String investmentType, Float totalValue,
                                Float quantity, Float unitPrice) {

    public static InvestmentRequest fromEntity(Investment investment) {
        return new InvestmentRequest(
                investment.getInvestmentId(),
                investment.getInvestmentType(),
                investment.getTotalValue(),
                investment.getQuantity(),
                investment.getUnitPrice()
        );
    }
}
