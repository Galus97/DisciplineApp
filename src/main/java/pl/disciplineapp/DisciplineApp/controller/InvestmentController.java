package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.service.InvestmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/investment")
public class InvestmentController {
    private final InvestmentService investmentService;
}
