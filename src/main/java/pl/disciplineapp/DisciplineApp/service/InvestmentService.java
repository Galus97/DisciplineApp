package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.response.InvestmentRequest;
import pl.disciplineapp.DisciplineApp.repository.InvestmentRepository;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final MessageService messageService;

    private void throwIfRequestIsNull(InvestmentRequest investmentRequest) {
        if (investmentRequest == null) {
           throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVESTMENT_REQUEST_IS_NULL));
        }
    }

    private void throwIfIdIsNotValid(Long id) {
        if (id == null || id <= 0) {
           throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_INVESTMENT_ID));
        }
    }
}
