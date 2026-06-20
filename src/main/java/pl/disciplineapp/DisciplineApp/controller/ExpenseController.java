package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.ExpenseRequest;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.service.ExpenseService;

import java.net.URI;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
