package pl.disciplineapp.DisciplineApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SavingNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSavingNotFoundException(SavingNotFoundException e) {
        return getMapResponseEntity(e);
    }

    private static ResponseEntity<Map<String, String>> getMapResponseEntity(RuntimeException exception) {
        Map<String, String> response = new HashMap<>();
        response.put(ErrorMessages.ERROR,  exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
