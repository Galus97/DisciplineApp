package pl.disciplineapp.DisciplineApp.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.model.Expense;
import pl.disciplineapp.DisciplineApp.exception.ExpenseNotFoundException;
import pl.disciplineapp.DisciplineApp.model.User;
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

    @Transactional(readOnly = true)
    public ExpenseResponse getExpenseResponse(Long expenseId, User user) {
        serviceValidator.throwIfIdIsNotValid(expenseId, ErrorMessages.INVALID_EXPENSE_ID);
        return ExpenseResponse.fromEntity(getExpenseOrThrowIfNotExist(expenseId, user));
    }

    @Transactional
    public ExpenseResponse saveExpense(ExpenseRequest expenseRequest, User user) {
        serviceValidator.throwIfRequestIsNull(expenseRequest, ErrorMessages.EXPENSE_REQUEST_IS_NULL);
        return ExpenseResponse.fromEntity(expenseRepository.save(buildExpense(expenseRequest, user)));
    }

    @Transactional
    public void deleteExpense(Long expenseId, User user) {
        serviceValidator.throwIfIdIsNotValid(expenseId, ErrorMessages.INVALID_EXPENSE_ID);
        expenseRepository.delete(getExpenseOrThrowIfNotExist(expenseId, user));
    }

    @Transactional
    public ExpenseResponse updateExpense(ExpenseRequest expenseRequest, User user) {
        serviceValidator.throwIfRequestIsNull(expenseRequest, ErrorMessages.EXPENSE_REQUEST_IS_NULL);
        serviceValidator.throwIfIdIsNotValid(expenseRequest.getExpenseId(), ErrorMessages.INVALID_EXPENSE_ID);

        Expense existingExpense = getExpenseOrThrowIfNotExist(expenseRequest.getExpenseId(), user);
        existingExpense.setExpenseType(expenseRequest.getExpenseType());
        existingExpense.setExpenseDescription(expenseRequest.getExpenseDescription());
        existingExpense.setTotalValue(expenseRequest.getTotalValue());
        existingExpense.setQuantity(expenseRequest.getQuantity());
        existingExpense.setUnitPrice(expenseRequest.getUnitPrice());
        //existingExpense.setCreatedAt(expenseRequest.getCreatedAt());
        //existingExpense.setUser(user);

        return ExpenseResponse.fromEntity(expenseRepository.save(existingExpense));
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getAllExpense(User user) {
        serviceValidator.throwIfIdIsNotValid(user.getUserId(), ErrorMessages.INVALID_USER_ID);
        return ExpenseResponse.fromEntityList(expenseRepository.findAllByUser(user));
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesBetweenDates(User user, String from, String to) {
        serviceValidator.throwIfIdIsNotValid(user.getUserId(), ErrorMessages.INVALID_USER_ID);
        //This throws exception if user doesn't exist
        userService.getUserOrThrowIfNotExist(user.getUserId());

        if (from == null || to == null) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_PARAMS);
        }

        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(from);
            LocalDateTime toDateTime = LocalDateTime.parse(to);
            return ExpenseResponse.fromEntityList(
                    expenseRepository.findAllByUserAndCreatedAtBetween(user, fromDateTime, toDateTime));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT_PARAMS);
        }
    }

    private Expense buildExpense(ExpenseRequest expenseRequest, User user) {
        return Expense.builder()
                .expenseType(expenseRequest.getExpenseType())
                .expenseDescription(expenseRequest.getExpenseDescription())
                .totalValue(expenseRequest.getTotalValue())
                .quantity(expenseRequest.getQuantity())
                .unitPrice(expenseRequest.getUnitPrice())
                .user(user)
                .build();
    }

    private Expense getExpenseOrThrowIfNotExist(Long expenseId, User user) {
        return expenseRepository.findByExpenseIdAndUser(expenseId, user)
                .orElseThrow(() -> new ExpenseNotFoundException(
                        messageService.getMessage(ErrorMessages.EXPENSE_NOT_FOUND, expenseId)));
    }
}
