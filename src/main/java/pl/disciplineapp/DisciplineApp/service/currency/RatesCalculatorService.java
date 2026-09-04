package pl.disciplineapp.DisciplineApp.service.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.external.CurrencyRate;
import pl.disciplineapp.DisciplineApp.dto.response.CurrencyConversionResponse;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RatesCalculatorService {
    private final CurrenciesClient currenciesClient;

    public List<CurrencyConversionResponse> getConversions(
            BigDecimal amount,
            String baseCurrency,
            List<String> currencies) {

        List<CurrencyRate> currenciesRates = currenciesClient.getCurrenciesRates(baseCurrency, currencies);

        return currenciesRates.stream()
                .map(rate -> new CurrencyConversionResponse(
                   baseCurrency,
                   rate.code(),
                   amount,
                   rate.value(),
                   rate.value().multiply(amount)
                )).toList();
    }
}
