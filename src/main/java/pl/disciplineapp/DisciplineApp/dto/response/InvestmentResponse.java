package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Investment;
import pl.disciplineapp.DisciplineApp.entity.User;

public record InvestmentResponse(Long investmentId, String investmentType, Float totalValue,
                                 Float quantity, Float unitPrice, User user) {

    public static InvestmentResponse fromEntity(Investment investment) {
        return new InvestmentResponse(
                investment.getInvestmentId(),
                investment.getInvestmentType(),
                investment.getTotalValue(),
                investment.getQuantity(),
                investment.getUnitPrice(),
                investment.getUser()
        );
    }
}
