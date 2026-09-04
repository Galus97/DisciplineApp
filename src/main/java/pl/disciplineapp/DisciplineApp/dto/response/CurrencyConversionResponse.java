package pl.disciplineapp.DisciplineApp.dto.response;

import java.math.BigDecimal;

public record CurrencyConversionResponse(
        String baseCurrency,
        String requestedCurrency,
        BigDecimal requestedAmount,
        BigDecimal rate,
        BigDecimal calculatedAmount) {
}
