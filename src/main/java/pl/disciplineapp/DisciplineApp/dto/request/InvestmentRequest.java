package pl.disciplineapp.DisciplineApp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class InvestmentRequest {
    private Long investmentId;
    @NotBlank
    private String investmentType;
    @NotNull
    private Float totalValue;
    @NotNull
    private Float quantity;
    @NotNull
    private Float unitPrice;
    @NotNull
    private Long userId;
}
