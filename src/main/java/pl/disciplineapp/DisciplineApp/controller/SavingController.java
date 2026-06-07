package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.service.SavingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/savong")
public class SavingController {
    private final SavingService savingService;
}
