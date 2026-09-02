package pl.disciplineapp.DisciplineApp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.InvestmentRequest;
import pl.disciplineapp.DisciplineApp.dto.response.InvestmentResponse;
import pl.disciplineapp.DisciplineApp.model.User;
import pl.disciplineapp.DisciplineApp.service.InvestmentService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/investment")
public class InvestmentController {
    private final InvestmentService investmentService;

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentResponse> showInvestment(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(investmentService.getInvestment(id, user));
    }

    @PostMapping
    public ResponseEntity<InvestmentResponse> saveInvestment(
            @RequestBody @Valid InvestmentRequest investmentRequest,
            @AuthenticationPrincipal User user) {

        InvestmentResponse savedInvestment = investmentService.saveInvestment(investmentRequest, user);

        return ResponseEntity.created(URI.create("/investment/" + savedInvestment.investmentId()))
                .body(savedInvestment);
    }

    @PutMapping
    public ResponseEntity<InvestmentResponse> updateInvestment(
            @RequestBody @Valid InvestmentRequest investmentRequest,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(investmentService.updateInvestment(investmentRequest, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        investmentService.deleteInvestment(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvestmentResponse>> getInvestments(
            @PathVariable Long userId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @AuthenticationPrincipal User user) {

        if (from != null && to != null) {
            return ResponseEntity.ok(investmentService.getInvestmentsBetweenDates(from, to, user));
        }
        return ResponseEntity.ok(investmentService.getAllInvestment(user));
    }
}
