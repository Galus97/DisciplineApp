package pl.disciplineapp.DisciplineApp.controller.allByUser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.dto.response.InvestmentResponse;
import pl.disciplineapp.DisciplineApp.service.InvestmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AllInvestmentByUserController {
    private final InvestmentService investmentService;

    @GetMapping("/{userId}/investments")
    public ResponseEntity<List<InvestmentResponse>> getAllInvestmentByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(investmentService.getAllInvestmentResponseByUser(userId));
    }
}
