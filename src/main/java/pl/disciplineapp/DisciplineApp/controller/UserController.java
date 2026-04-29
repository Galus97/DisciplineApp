package pl.disciplineapp.DisciplineApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.disciplineapp.DisciplineApp.dto.request.UserRequest;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.exception.ValidationException;
import pl.disciplineapp.DisciplineApp.service.UserService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> showUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserResponse(id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse savedUser = userService.saveNewUser(userRequest);
            return ResponseEntity.created(URI.create("/user/" + savedUser.userId())).body(savedUser);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getValidationsErrors());
        }
    }
}

