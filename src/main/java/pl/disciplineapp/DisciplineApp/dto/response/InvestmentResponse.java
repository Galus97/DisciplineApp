package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Investment;
import pl.disciplineapp.DisciplineApp.entity.User;

import java.util.ArrayList;
import java.util.List;

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

    public static List<InvestmentResponse> fromEntityList(List<Investment> investmentList) {
        List<InvestmentResponse> investmentResponseList = new ArrayList<>();

        if (!investmentList.isEmpty()) {
            for (Investment investment : investmentList) {
                investmentResponseList.add(fromEntity(investment));
            }
        }
        return investmentResponseList;
    }
}
