package pl.disciplineapp.DisciplineApp.dto.response;

public record SavingResponse(Long savingId, String savingType, Float TotalValue,
                            Float quantity, Float unitPrice) {

}
