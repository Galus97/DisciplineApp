package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.SavingRequest;
import pl.disciplineapp.DisciplineApp.dto.response.SavingResponse;
import pl.disciplineapp.DisciplineApp.service.SavingService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saving")
public class SavingController {
    private final SavingService savingService;

    @GetMapping("/{id}")
    public ResponseEntity<SavingResponse> showSaving(@PathVariable Long id) {
        return ResponseEntity.ok(savingService.getSavingResponse(id));
    }

    @PostMapping
    public ResponseEntity<SavingResponse> createSaving(@RequestBody SavingRequest savingRequest) {
        SavingResponse savedSaving = savingService.saveSaving(savingRequest);
        return ResponseEntity.created(URI.create("/saving/" + savedSaving.savingId())).body(savedSaving);
    }

    @PutMapping
    public ResponseEntity<SavingResponse> updateSaving(@RequestBody SavingRequest savingRequest) {
        return ResponseEntity.ok(savingService.updateSaving(savingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaving(@PathVariable Long id) {
        savingService.deleteSaving(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<SavingResponse>> getAllSavings(@PathVariable Long userId) {
        return ResponseEntity.ok(savingService.getAllSaving(userId));
    }
}
