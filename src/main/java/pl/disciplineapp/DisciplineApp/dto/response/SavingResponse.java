package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Saving;
import pl.disciplineapp.DisciplineApp.entity.User;

public record SavingResponse(Long savingId, String savingType, Float TotalValue,
                             Float quantity, Float unitPrice, User user) {
    public static SavingResponse fromEntity(Saving saving) {
        return new SavingResponse(
                saving.getSavingId(),
                saving.getSavingType(),
                saving.getTotalValue(),
                saving.getQuantity(),
                saving.getUnitPrice(),
                saving.getUser()
        );
    }
}
