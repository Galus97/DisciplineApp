package pl.disciplineapp.DisciplineApp.dto.request;

import lombok.Builder;
import lombok.Getter;
import pl.disciplineapp.DisciplineApp.entity.User;

@Getter
@Builder
public class ExpenseRequest {
    private Long expenseId;
    private String expenseType;
    private Float totalValue;
    private Float quantity;
    private Float unitPrice;
    private Long userId;
}
