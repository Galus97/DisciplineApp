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
import pl.disciplineapp.DisciplineApp.dto.response.UserLoginResponse;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.exception.ValidationException;
import pl.disciplineapp.DisciplineApp.security.AuthenticationService;
import pl.disciplineapp.DisciplineApp.service.auth.RegistrationUserService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationUserService registrationService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody @Valid UserLoginRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationRequest request){
        try {
            UserResponse userResponse = registrationService.saveNewUser(request);
            return ResponseEntity
                    .created(URI.create("/auth/registration/" + userResponse.userId()))
                    .body(userResponse);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getValidationsErrors());
        }
    }
}
