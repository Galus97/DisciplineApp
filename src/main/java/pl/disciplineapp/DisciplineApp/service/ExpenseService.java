package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.entity.Expense;
import pl.disciplineapp.DisciplineApp.exception.ExpenseNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.ExpenseRepository;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final MessageService messageService;

    public ExpenseResponse getExpenseResponse(Long expenseId) {
        throwIfIdIsNotValid(expenseId);
        return ExpenseResponse.fromEntity(getExpenseOrThrowIfNotExist(expenseId));
    }

    public ExpenseResponse saveExpense(ExpenseRequest expenseRequest) {
        throwIfRequestIsNull(expenseRequest);
        return ExpenseResponse.fromEntity(expenseRepository.save(buildExpense(expenseRequest)));
    }

    private Expense buildExpense(ExpenseRequest expenseRequest) {
        return Expense.builder()
                .expenseType(expenseRequest.getExpenseType())
                .totalValue(expenseRequest.getTotalValue())
                .quantity(expenseRequest.getQuantity())
                .unitPrice(expenseRequest.getUnitPrice())
                .build();
    }

    private void throwIfRequestIsNull(ExpenseRequest expenseRequest) {
        if (expenseRequest == null) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.EXPENSE_REQUEST_IS_NULL));
        }
    }

    private Expense getExpenseOrThrowIfNotExist(Long expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow(
                () -> new ExpenseNotFoundException(messageService.getMessage(ErrorMessages.EXPENSE_NOT_FOUND)));
    }

    private void throwIfIdIsNotValid(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_EXPENSE_ID));
        }
    }
}
