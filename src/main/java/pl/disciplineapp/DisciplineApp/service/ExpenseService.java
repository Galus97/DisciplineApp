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
import pl.disciplineapp.DisciplineApp.util.ServiceValidator;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ServiceValidator serviceValidator;
    private final MessageService messageService;

    public ExpenseResponse getExpenseResponse(Long expenseId) {
        serviceValidator.throwIfIdIsNotValid(expenseId, ErrorMessages.INVALID_EXPENSE_ID);
        return ExpenseResponse.fromEntity(getExpenseOrThrowIfNotExist(expenseId));
    }

    public ExpenseResponse saveExpense(ExpenseRequest expenseRequest) {
        serviceValidator.throwIfRequestIsNull(expenseRequest, ErrorMessages.EXPENSE_REQUEST_IS_NULL);
        return ExpenseResponse.fromEntity(expenseRepository.save(buildExpense(expenseRequest)));
    }

    public void deleteExpense(Long expenseId) {
        serviceValidator.throwIfIdIsNotValid(expenseId, ErrorMessages.INVALID_EXPENSE_ID);
        expenseRepository.delete(getExpenseOrThrowIfNotExist(expenseId));
    }

    public ExpenseResponse updateExpense(ExpenseRequest expenseRequest) {
        serviceValidator.throwIfRequestIsNull(expenseRequest, ErrorMessages.EXPENSE_REQUEST_IS_NULL);

        Expense existingExpense = getExpenseOrThrowIfNotExist(expenseRequest.getExpenseId());
        existingExpense.setExpenseType(existingExpense.getExpenseType());
        existingExpense.setTotalValue(existingExpense.getTotalValue());
        existingExpense.setQuantity(existingExpense.getQuantity());
        existingExpense.setUnitPrice(existingExpense.getUnitPrice());

        return ExpenseResponse.fromEntity(expenseRepository.save(existingExpense));
    }

    private Expense buildExpense(ExpenseRequest expenseRequest) {
        return Expense.builder()
                .expenseType(expenseRequest.getExpenseType())
                .totalValue(expenseRequest.getTotalValue())
                .quantity(expenseRequest.getQuantity())
                .unitPrice(expenseRequest.getUnitPrice())
                .build();
    }

    private Expense getExpenseOrThrowIfNotExist(Long expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow(
                () -> new ExpenseNotFoundException(messageService.getMessage(ErrorMessages.EXPENSE_NOT_FOUND)));
    }
}
