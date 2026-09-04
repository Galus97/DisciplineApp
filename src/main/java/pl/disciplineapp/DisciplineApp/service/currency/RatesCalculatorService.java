package pl.disciplineapp.DisciplineApp.service.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.external.CurrencyRateResponse;
import pl.disciplineapp.DisciplineApp.dto.internal.CurrencyConversionDto;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RatesCalculatorService {
    private final CurrenciesClient currenciesClient;

    public List<CurrencyConversionDto> getConversions(
            BigDecimal amount,
            String baseCurrency,
            List<String> currencies) {

        List<CurrencyRateResponse> currenciesRates = currenciesClient.getCurrenciesRates(baseCurrency, currencies);

        return currenciesRates.stream()
                .map(rate -> new CurrencyConversionDto(
                   baseCurrency,
                   rate.code(),
                   amount,
                   rate.value(),
                   rate.value().multiply(amount)
                )).toList();
    }
}
