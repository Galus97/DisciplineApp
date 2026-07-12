package pl.disciplineapp.DisciplineApp.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavingRequest {
    private Long savingId;
    private String savingType;
    private Float TotalValue;
    private Float quantity;
    private Float unitPrice;
    private Long userId;
}
