package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.repository.SavingsRepository;

@Service
@RequiredArgsConstructor
public class SavingsService {
    private final SavingsRepository savingsRepository;
}
