package pl.disciplineapp.DisciplineApp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.disciplineapp.DisciplineApp.dto.request.SavingRequest;
import pl.disciplineapp.DisciplineApp.dto.response.SavingResponse;
import pl.disciplineapp.DisciplineApp.model.User;
import pl.disciplineapp.DisciplineApp.service.SavingService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saving")
public class SavingController {
    private final SavingService savingService;

    @GetMapping("/{id}")
    public ResponseEntity<SavingResponse> showSaving(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(savingService.getSavingResponse(id, user));
    }

    @PostMapping
    public ResponseEntity<SavingResponse> createSaving(
            @RequestBody @Valid SavingRequest savingRequest,
            @AuthenticationPrincipal User user) {
        SavingResponse savedSaving = savingService.saveSaving(savingRequest, user);
        return ResponseEntity.created(URI.create("/saving/" + savedSaving.savingId())).body(savedSaving);
    }

    @PutMapping
    public ResponseEntity<SavingResponse> updateSaving(
            @RequestBody @Valid SavingRequest savingRequest,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(savingService.updateSaving(savingRequest, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaving(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        savingService.deleteSaving(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<SavingResponse>> getSavings(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @AuthenticationPrincipal User user,
            Pageable pageable) {
        if (from != null && to != null) {
            return ResponseEntity.ok(savingService.getSavingBetweenDates(user, from, to, pageable));
        }
        return ResponseEntity.ok(savingService.getAllSaving(user, pageable));
    }
}
