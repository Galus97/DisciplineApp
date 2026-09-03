package pl.disciplineapp.DisciplineApp.dto.external;

import java.math.BigDecimal;

public record CurrencyRateResponse(String code, BigDecimal value) {
}
