package pl.disciplineapp.DisciplineApp.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SavingRequest {
    private Long savingId;
    private String savingType;
    private Float TotalValue;
    private Float quantity;
    private Float unitPrice;
    private LocalDateTime createdAt;
    private Long userId;
}
