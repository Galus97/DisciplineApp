package pl.disciplineapp.DisciplineApp.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.mapper.ExpenseMapper;
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
        return ExpenseMapper.toExpenseResponse(getExpenseOrThrowIfNotExist(expenseId, user));
    }

    @Transactional
    public ExpenseResponse saveExpense(ExpenseRequest expenseRequest, User user) {
        serviceValidator.throwIfRequestIsNull(expenseRequest, ErrorMessages.EXPENSE_REQUEST_IS_NULL);
        Expense expense = expenseRepository.save(ExpenseMapper.toExpenseModel(expenseRequest, user));

        return ExpenseMapper.toExpenseResponse(expense);
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

        return ExpenseMapper.toExpenseResponse(expenseRepository.save(existingExpense));
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getAllExpense(User user) {
        serviceValidator.throwIfIdIsNotValid(user.getUserId(), ErrorMessages.INVALID_USER_ID);

        return expenseRepository.findAllByUser(user)
                .stream()
                .map(ExpenseMapper::toExpenseResponse)
                .toList();
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

            return expenseRepository.findAllByUserAndCreatedAtBetween(user, fromDateTime, toDateTime)
                    .stream()
                    .map(ExpenseMapper::toExpenseResponse)
                    .toList();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_FORMAT_PARAMS);
        }
    }

    private Expense getExpenseOrThrowIfNotExist(Long expenseId, User user) {
        return expenseRepository.findByExpenseIdAndUser(expenseId, user)
                .orElseThrow(() -> new ExpenseNotFoundException(
                        messageService.getMessage(ErrorMessages.EXPENSE_NOT_FOUND, expenseId)));
    }
}
