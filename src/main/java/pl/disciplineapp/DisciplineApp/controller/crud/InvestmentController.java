package pl.disciplineapp.DisciplineApp.controller.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.InvestmentRequest;
import pl.disciplineapp.DisciplineApp.dto.response.InvestmentResponse;
import pl.disciplineapp.DisciplineApp.service.InvestmentService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/investment")
public class InvestmentController {
    private final InvestmentService investmentService;

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentResponse> showInvestment(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.getInvestment(id));
    }

    @PostMapping
    public ResponseEntity<InvestmentResponse> saveInvestment(@RequestBody InvestmentRequest investmentRequest) {
        InvestmentResponse savedInvestment = investmentService.saveInvestment(investmentRequest);
        return ResponseEntity.created(URI.create("/investment/" + savedInvestment.investmentId()))
                .body(savedInvestment);
    }

    @PutMapping
    public ResponseEntity<InvestmentResponse> updateInvestment(@RequestBody InvestmentRequest investmentRequest) {
        return ResponseEntity.ok(investmentService.updateInvestment(investmentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id);
        return ResponseEntity.noContent().build();
    }
}
