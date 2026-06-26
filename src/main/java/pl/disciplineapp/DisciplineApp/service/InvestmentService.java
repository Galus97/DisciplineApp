package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.InvestmentRequest;
import pl.disciplineapp.DisciplineApp.dto.response.InvestmentResponse;
import pl.disciplineapp.DisciplineApp.entity.Investment;
import pl.disciplineapp.DisciplineApp.exception.InvestmentNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.InvestmentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final MessageService messageService;

    public InvestmentResponse getInvestment(Long id) {
        throwIfIdIsNotValid(id);
        return InvestmentResponse.fromEntity(getInvestmentOrThrowIfNotExist(id));
    }

    public InvestmentResponse saveInvestment(InvestmentRequest investmentRequest) {
        throwIfRequestIsNull(investmentRequest);
        return InvestmentResponse.fromEntity(investmentRepository.save(buildInvestment(investmentRequest)));
    }

    public InvestmentResponse updateInvestment(InvestmentRequest investmentRequest) {
        throwIfRequestIsNull(investmentRequest);

        Investment existingInvestment = getInvestmentOrThrowIfNotExist(investmentRequest.getInvestmentId());
        existingInvestment.setInvestmentType(investmentRequest.getInvestmentType());
        existingInvestment.setTotalValue(investmentRequest.getTotalValue());
        existingInvestment.setQuantity(investmentRequest.getQuantity());
        existingInvestment.setUnitPrice(investmentRequest.getUnitPrice());

        return InvestmentResponse.fromEntity(investmentRepository.save(existingInvestment));
    }

    public void deleteInvestment(Long id) {
        throwIfIdIsNotValid(id);
        investmentRepository.delete(getInvestmentOrThrowIfNotExist(id));
    }

    private Investment buildInvestment(InvestmentRequest investmentRequest) {
        return Investment.builder()
                .investmentType(investmentRequest.getInvestmentType())
                .totalValue(investmentRequest.getTotalValue())
                .quantity(investmentRequest.getQuantity())
                .unitPrice(investmentRequest.getUnitPrice())
                .build();
    }

    private Investment getInvestmentOrThrowIfNotExist(Long id) {
        return investmentRepository.findById(id).orElseThrow(
                () -> new InvestmentNotFoundException(messageService.getMessage(ErrorMessages.INVESTMENT_NOT_FOUND)));
    }

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
