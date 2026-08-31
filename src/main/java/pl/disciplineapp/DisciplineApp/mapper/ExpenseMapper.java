package pl.disciplineapp.DisciplineApp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.model.Expense;
import pl.disciplineapp.DisciplineApp.model.User;
import pl.disciplineapp.DisciplineApp.service.UserService;

@Component
@RequiredArgsConstructor
public class ExpenseMapper {
    private final UserService userService;

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
