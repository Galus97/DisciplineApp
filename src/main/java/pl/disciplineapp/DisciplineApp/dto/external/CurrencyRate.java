package pl.disciplineapp.DisciplineApp.dto.external;

import java.math.BigDecimal;

public record CurrencyRate(String code, BigDecimal value) {
}
