package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.SavingRequest;
import pl.disciplineapp.DisciplineApp.dto.response.SavingResponse;
import pl.disciplineapp.DisciplineApp.service.SavingService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saving")
public class SavingController {
    private final SavingService savingService;

    @GetMapping("/id")
    public ResponseEntity<SavingResponse> showSaving(@PathVariable Long id) {
        return ResponseEntity.ok(savingService.getSavingResponse(id));
    }

    @PostMapping
    public ResponseEntity<SavingResponse> saveSaving(@RequestBody SavingRequest savingRequest) {
        SavingResponse savedSaving = savingService.saveSaving(savingRequest);
        return ResponseEntity.created(URI.create("/saving/" + savedSaving.savingId())).body(savedSaving);
    }
}
