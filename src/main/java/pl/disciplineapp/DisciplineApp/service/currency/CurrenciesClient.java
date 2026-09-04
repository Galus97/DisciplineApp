package pl.disciplineapp.DisciplineApp.service.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.component.TextMessages;
import pl.disciplineapp.DisciplineApp.dto.external.CurrencyResponseDataDto;
import pl.disciplineapp.DisciplineApp.dto.external.CurrencyRateResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CurrenciesClient {
    private final ObjectMapper objectMapper;

    @Value("${currency.api.key}")
    private String apiKey;

    public List<CurrencyRateResponse> getCurrenciesRates(String baseCurrency, List<String> currencies) {
        HttpClient httpClient = HttpClient.newHttpClient();

        String url = TextMessages.BASE_URL.formatted(
                apiKey, String.join(TextMessages.CURRENCY_SEPARATOR, currencies), baseCurrency);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            CurrencyResponseDataDto dataDto = objectMapper.readValue(response.body(), CurrencyResponseDataDto.class);
            return dataDto.getData().values().stream().toList();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
