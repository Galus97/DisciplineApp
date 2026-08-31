package pl.disciplineapp.DisciplineApp.dto.response;

import java.time.LocalDateTime;

public record ExpenseResponse(Long expenseId, String expenseType, String expenseDescription, Float totalValue,
                              Float quantity, Float unitPrice, LocalDateTime createdAt, Long userId) {
}
