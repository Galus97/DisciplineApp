package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.Saving;
import pl.disciplineapp.DisciplineApp.entity.User;

import java.util.ArrayList;
import java.util.List;

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

    public static List<SavingResponse> fromEntityList(List<Saving> savingList) {
        List<SavingResponse> savingResponseList = new ArrayList<>();

        if (!savingList.isEmpty()) {
            for (Saving saving : savingList) {
                savingResponseList.add(fromEntity(saving));
            }
        }
        return savingResponseList;
    }
}
