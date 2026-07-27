package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.model.Expense;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record ExpenseResponse(Long expenseId, String expenseType, Float totalValue, Float quantity,
                              Float unitPrice, LocalDateTime createdAt, Long userId) {
    public static ExpenseResponse fromEntity(Expense expense) {
        return new ExpenseResponse(
                expense.getExpenseId(),
                expense.getExpenseType(),
                expense.getTotalValue(),
                expense.getQuantity(),
                expense.getUnitPrice(),
                expense.getCreatedAt(),
                expense.getUser() != null ? expense.getUser().getUserId() : null
        );
    }

    public static List<ExpenseResponse> fromEntityList(List<Expense> expenseList) {
        return expenseList.stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
