package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.service.ExpenseService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> showExpense(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseResponse(id));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> saveExpense(@RequestBody ExpenseRequest expenseRequest) {
        ExpenseResponse savedExpense = expenseService.saveExpense(expenseRequest);
        return ResponseEntity.created(URI.create("/expense/" + savedExpense.expenseId())).body(savedExpense);
    }

    @PutMapping
    public ResponseEntity<ExpenseResponse> updateExpense(@RequestBody ExpenseRequest expenseRequest) {
        return ResponseEntity.ok(expenseService.updateExpense(expenseRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.getAllExpense(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getExpenses(
            @PathVariable Long userId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {

        if (from != null && to != null) {
            return ResponseEntity.ok(expenseService.getExpensesBetweenDates(userId, from, to));
        }
        return ResponseEntity.ok(expenseService.getAllExpense(userId));
    }
}
