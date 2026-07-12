package pl.disciplineapp.DisciplineApp.controller.allByUser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.dto.response.ExpenseResponse;
import pl.disciplineapp.DisciplineApp.service.ExpenseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AllExpenseByUserController {
    private final ExpenseService expenseService;

    @GetMapping("/{userId}/expenses")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenseByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.getAllExpenseResponseByUser(userId));
    }
}
