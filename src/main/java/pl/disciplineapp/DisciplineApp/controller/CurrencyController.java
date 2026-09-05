package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.dto.response.CurrencyConversionResponse;
import pl.disciplineapp.DisciplineApp.service.currency.RatesCalculatorService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final RatesCalculatorService ratesCalculatorService;

    @GetMapping
    public List<CurrencyConversionResponse> getConversions(
            @RequestParam BigDecimal amount,
            @RequestParam String currency,
            @RequestParam List<String> currencies
            ) {

        return ratesCalculatorService.getConversions(amount, currency, currencies);
    }
}
