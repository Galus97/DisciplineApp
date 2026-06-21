package pl.disciplineapp.DisciplineApp.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvestmentResponse {
    private Long investmentId;
    private String investmentType;
    private Float totalValue;
    private Float quantity;
    private Float unitPrice;
}
