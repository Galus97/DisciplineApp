package pl.disciplineapp.DisciplineApp.mapper;

import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.request.InvestmentRequest;
import pl.disciplineapp.DisciplineApp.dto.response.InvestmentResponse;
import pl.disciplineapp.DisciplineApp.model.Investment;
import pl.disciplineapp.DisciplineApp.model.User;

@Component
public class InvestmentMapper {

    public static InvestmentResponse toInvestmentResponse(Investment investment) {
        return new InvestmentResponse(
                investment.getInvestmentId(),
                investment.getInvestmentType(),
                investment.getTotalValue(),
                investment.getQuantity(),
                investment.getUnitPrice(),
                investment.getCreatedAt(),
                investment.getUser().getUserId()
        );
    }

    public static Investment toInvestmentModel(InvestmentRequest investmentRequest, User user) {
        return Investment.builder()
                .investmentId(investmentRequest.getInvestmentId())
                .investmentType(investmentRequest.getInvestmentType())
                .totalValue(investmentRequest.getTotalValue())
                .quantity(investmentRequest.getQuantity())
                .unitPrice(investmentRequest.getUnitPrice())
                .user(user)
                .build();
    }
}
