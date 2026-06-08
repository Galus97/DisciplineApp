package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.dto.response.SavingResponse;
import pl.disciplineapp.DisciplineApp.service.SavingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saving")
public class SavingController {
    private final SavingService savingService;

    @GetMapping("/id")
    public ResponseEntity<SavingResponse> showSaving(@PathVariable Long id) {
        return ResponseEntity.ok(savingService.getSavingResponse(id));
    }
}
