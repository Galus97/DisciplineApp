package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.service.ExpenseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> showExpense(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseResponse(id));
    }
}
