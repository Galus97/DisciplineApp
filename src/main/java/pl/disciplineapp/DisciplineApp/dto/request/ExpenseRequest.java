package pl.disciplineapp.DisciplineApp.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExpenseRequest {
    private Long expenseId;
    private String expenseType;
    private String expenseDescription;
    private Float totalValue;
    private Float quantity;
    private Float unitPrice;
    private LocalDateTime createdAt;
    private Long userId;
}
