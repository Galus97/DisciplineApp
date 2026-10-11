package pl.disciplineapp.DisciplineApp.mapper;

import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.model.Expense;
import pl.disciplineapp.DisciplineApp.model.User;

@Component
public class ExpenseMapper {

    public static ExpenseResponse toExpenseResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getExpenseId(),
                expense.getExpenseType(),
                expense.getExpenseDescription(),
                expense.getTotalValue(),
                expense.getQuantity(),
                expense.getUnitPrice(),
                expense.getCreatedAt(),
                expense.getUser() != null ? expense.getUser().getUserId() : null
        );
    }

    public static Expense toExpenseModel(ExpenseRequest expenseRequest, User user) {
        return Expense.builder()
                .expenseId(expenseRequest.getExpenseId())
                .expenseType(expenseRequest.getExpenseType())
                .expenseDescription(expenseRequest.getExpenseDescription())
                .totalValue(expenseRequest.getTotalValue())
                .quantity(expenseRequest.getQuantity())
                .unitPrice(expenseRequest.getUnitPrice())
                .user(user)
                .build();
    }
}
