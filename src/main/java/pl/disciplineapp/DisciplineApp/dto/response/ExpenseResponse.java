package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Expense;
import pl.disciplineapp.DisciplineApp.entity.User;

public record ExpenseResponse(Long expenseId, String expenseType, Float totalValue, Float quantity,
                              Float unitPrice, User user) {
    public static ExpenseResponse fromEntity(Expense expense) {
        return new ExpenseResponse(
                expense.getExpenseId(),
                expense.getExpenseType(),
                expense.getTotalValue(),
                expense.getQuantity(),
                expense.getUnitPrice(),
                expense.getUser()
        );
    }
}
