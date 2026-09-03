package pl.disciplineapp.DisciplineApp.dto.external;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyResponseDataDto {
    private Map<String, CurrencyRateResponse> data;
}
