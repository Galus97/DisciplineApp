package pl.disciplineapp.DisciplineApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.InvestmentRequest;
import pl.disciplineapp.DisciplineApp.dto.response.InvestmentResponse;
import pl.disciplineapp.DisciplineApp.entity.Investment;
import pl.disciplineapp.DisciplineApp.exception.InvestmentNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.InvestmentRepository;
import pl.disciplineapp.DisciplineApp.util.ServiceValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final UserService userService;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;

    public InvestmentResponse getInvestment(Long investmentId) {
        serviceValidator.throwIfIdIsNotValid(investmentId, ErrorMessages.INVALID_INVESTMENT_ID);
        return InvestmentResponse.fromEntity(getInvestmentOrThrowIfNotExist(investmentId));
    }

    @Transactional
    public InvestmentResponse saveInvestment(InvestmentRequest investmentRequest) {
        serviceValidator.throwIfRequestIsNull(investmentRequest, ErrorMessages.INVESTMENT_REQUEST_IS_NULL);
        return InvestmentResponse.fromEntity(investmentRepository.save(buildInvestment(investmentRequest)));
    }

    @Transactional
    public InvestmentResponse updateInvestment(InvestmentRequest investmentRequest) {
        serviceValidator.throwIfRequestIsNull(investmentRequest, ErrorMessages.INVESTMENT_REQUEST_IS_NULL);
        serviceValidator.throwIfIdIsNotValid(investmentRequest.getInvestmentId(), ErrorMessages.INVALID_INVESTMENT_ID);

        Investment existingInvestment = getInvestmentOrThrowIfNotExist(investmentRequest.getInvestmentId());
        existingInvestment.setInvestmentType(investmentRequest.getInvestmentType());
        existingInvestment.setTotalValue(investmentRequest.getTotalValue());
        existingInvestment.setQuantity(investmentRequest.getQuantity());
        existingInvestment.setUnitPrice(investmentRequest.getUnitPrice());
        existingInvestment.setUser(userService.getUserOrThrowIfNotExist(investmentRequest.getUserId()));

        return InvestmentResponse.fromEntity(investmentRepository.save(existingInvestment));
    }

    @Transactional
    public void deleteInvestment(Long investmentId) {
        serviceValidator.throwIfIdIsNotValid(investmentId, ErrorMessages.INVALID_INVESTMENT_ID);
        investmentRepository.delete(getInvestmentOrThrowIfNotExist(investmentId));
    }

    public List<InvestmentResponse> getAllInvestment(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return InvestmentResponse.fromEntityList(investmentRepository.findAllByUser_UserId(userId));
    }


    private Investment buildInvestment(InvestmentRequest investmentRequest) {
        return Investment.builder()
                .investmentType(investmentRequest.getInvestmentType())
                .totalValue(investmentRequest.getTotalValue())
                .quantity(investmentRequest.getQuantity())
                .unitPrice(investmentRequest.getUnitPrice())
                .user(userService.getUserOrThrowIfNotExist(investmentRequest.getUserId()))
                .build();
    }

    private Investment getInvestmentOrThrowIfNotExist(Long id) {
        return investmentRepository.findById(id).orElseThrow(
                () -> new InvestmentNotFoundException(messageService.getMessage(ErrorMessages.INVESTMENT_NOT_FOUND)));
    }
}
