package pl.disciplineapp.DisciplineApp.dto.external;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyApiResponseDto {
    private Map<String, CurrencyRate> data;
}
