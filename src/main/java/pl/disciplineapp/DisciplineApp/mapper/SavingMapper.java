package pl.disciplineapp.DisciplineApp.mapper;

import org.springframework.stereotype.Component;
import pl.disciplineapp.DisciplineApp.dto.response.SavingResponse;
import pl.disciplineapp.DisciplineApp.model.Saving;

@Component
public class SavingMapper {

    public static SavingResponse toSavingResponse(Saving saving) {
        return new SavingResponse(
                saving.getSavingId(),
                saving.getSavingType(),
                saving.getTotalValue(),
                saving.getQuantity(),
                saving.getUnitPrice(),
                saving.getCreatedAt(),
                saving.getUser().getUserId()
        );
    }
}
