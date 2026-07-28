package pl.disciplineapp.DisciplineApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.model.Expense;
import pl.disciplineapp.DisciplineApp.exception.ExpenseNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.ExpenseRepository;
import pl.disciplineapp.DisciplineApp.util.ServiceValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final ServiceValidator serviceValidator;
    private final MessageService messageService;

    public ExpenseResponse getExpenseResponse(Long expenseId) {
        serviceValidator.throwIfIdIsNotValid(expenseId, ErrorMessages.INVALID_EXPENSE_ID);
        return ExpenseResponse.fromEntity(getExpenseOrThrowIfNotExist(expenseId));
    }

    @Transactional
    public ExpenseResponse saveExpense(ExpenseRequest expenseRequest) {
        serviceValidator.throwIfRequestIsNull(expenseRequest, ErrorMessages.EXPENSE_REQUEST_IS_NULL);
        return ExpenseResponse.fromEntity(expenseRepository.save(buildExpense(expenseRequest)));
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        serviceValidator.throwIfIdIsNotValid(expenseId, ErrorMessages.INVALID_EXPENSE_ID);
        expenseRepository.delete(getExpenseOrThrowIfNotExist(expenseId));
    }

    @Transactional
    public ExpenseResponse updateExpense(ExpenseRequest expenseRequest) {
        serviceValidator.throwIfRequestIsNull(expenseRequest, ErrorMessages.EXPENSE_REQUEST_IS_NULL);
        serviceValidator.throwIfIdIsNotValid(expenseRequest.getExpenseId(), ErrorMessages.INVALID_EXPENSE_ID);

        Expense existingExpense = getExpenseOrThrowIfNotExist(expenseRequest.getExpenseId());
        existingExpense.setExpenseType(expenseRequest.getExpenseType());
        existingExpense.setTotalValue(expenseRequest.getTotalValue());
        existingExpense.setQuantity(expenseRequest.getQuantity());
        existingExpense.setUnitPrice(expenseRequest.getUnitPrice());
        existingExpense.setCreatedAt(expenseRequest.getCreatedAt());
        existingExpense.setUser(userService.getUserOrThrowIfNotExist(expenseRequest.getUserId()));

        return ExpenseResponse.fromEntity(expenseRepository.save(existingExpense));
    }

    public List<ExpenseResponse> getAllExpense(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return ExpenseResponse.fromEntityList(expenseRepository.findAllByUser_UserId(userId));
    }

    public List<ExpenseResponse> getExpensesBetweenDates(Long userId, String from, String to) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        //This throws exception if user doesn't exist
        userService.getUserOrThrowIfNotExist(userId);

        if (from == null || to == null) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_PARAMS);
        }

        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(from);
            LocalDateTime toDateTime = LocalDateTime.parse(to);
            return ExpenseResponse.fromEntityList(
                    expenseRepository.findAllByUserIdAndCreatedAtBetween(userId, fromDateTime, toDateTime));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT_PARAMS);
        }
    }

    private Expense buildExpense(ExpenseRequest expenseRequest) {
        return Expense.builder()
                .expenseType(expenseRequest.getExpenseType())
                .totalValue(expenseRequest.getTotalValue())
                .quantity(expenseRequest.getQuantity())
                .unitPrice(expenseRequest.getUnitPrice())
                .createdAt(expenseRequest.getCreatedAt())
                .user(userService.getUserOrThrowIfNotExist(expenseRequest.getUserId()))
                .build();
    }

    private Expense getExpenseOrThrowIfNotExist(Long expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow(
                () -> new ExpenseNotFoundException(messageService.getMessage(
                        ErrorMessages.EXPENSE_NOT_FOUND, expenseId)));
    }
}
