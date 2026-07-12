package pl.disciplineapp.DisciplineApp.dto.request;

import lombok.Builder;
import lombok.Getter;
import pl.disciplineapp.DisciplineApp.entity.User;

@Getter
@Builder
public class InvestmentRequest {
    private Long investmentId;
    private String investmentType;
    private Float totalValue;
    private Float quantity;
    private Float unitPrice;
    private Long userId;
}
