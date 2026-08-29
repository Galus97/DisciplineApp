package pl.disciplineapp.DisciplineApp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SavingRequest {
    private Long savingId;
    @NotBlank
    private String savingType;
    @NotNull
    private Float TotalValue;
    @NotNull
    private Float quantity;
    @NotNull
    private Float unitPrice;
    @NotNull
    private Long userId;
}
