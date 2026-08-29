package pl.disciplineapp.DisciplineApp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ExpenseRequest {
    private Long expenseId;
    @NotBlank
    private String expenseType;
    @NotBlank
    private String expenseDescription;
    @NotNull
    private Float totalValue;
    @NotNull
    private Float quantity;
    @NotNull
    private Float unitPrice;
    @NotNull
    private Long userId;
}
