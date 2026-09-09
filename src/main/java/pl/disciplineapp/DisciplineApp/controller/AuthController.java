package pl.disciplineapp.DisciplineApp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.dto.request.UserLoginRequest;
import pl.disciplineapp.DisciplineApp.dto.request.UserRegistrationRequest;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok().build();
    }

    
}
