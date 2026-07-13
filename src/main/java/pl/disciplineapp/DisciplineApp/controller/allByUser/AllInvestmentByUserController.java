package pl.disciplineapp.DisciplineApp.controller.allByUser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.service.InvestmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AllInvestmentByUserController {
    private final InvestmentService investmentService;
}
