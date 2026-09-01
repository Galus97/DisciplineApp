package pl.disciplineapp.DisciplineApp.mapper;

import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.response.InvestmentResponse;
import pl.disciplineapp.DisciplineApp.model.Investment;

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
}
