package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Expense;
import pl.disciplineapp.DisciplineApp.entity.User;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ExpenseResponse> fromEntityList(List<Expense> expenseList) {
        List<ExpenseResponse> expenseResponseList = new ArrayList<>();
        if (!expenseList.isEmpty()) {
            for (Expense expense : expenseList) {
                expenseResponseList.add(fromEntity(expense));
            }
        }
        return expenseResponseList;
    }
}
