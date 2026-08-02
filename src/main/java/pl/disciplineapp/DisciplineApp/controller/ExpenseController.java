package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.model.User;
import pl.disciplineapp.DisciplineApp.service.ExpenseService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> showExpense(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(expenseService.getExpenseResponse(id, user));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> saveExpense(@RequestBody ExpenseRequest expenseRequest, @AuthenticationPrincipal User user) {
        ExpenseResponse savedExpense = expenseService.saveExpense(expenseRequest, user);
        return ResponseEntity.created(URI.create("/expense/" + savedExpense.expenseId())).body(savedExpense);
    }

    @PutMapping
    public ResponseEntity<ExpenseResponse> updateExpense(@RequestBody ExpenseRequest expenseRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(expenseService.updateExpense(expenseRequest, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, @AuthenticationPrincipal User user) {
        expenseService.deleteExpense(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<ExpenseResponse>> getExpenses(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @AuthenticationPrincipal User user) {

        if (from != null && to != null) {
            return ResponseEntity.ok(expenseService.getExpensesBetweenDates(user, from, to));
        }
        return ResponseEntity.ok(expenseService.getAllExpense(user));
    }
}
