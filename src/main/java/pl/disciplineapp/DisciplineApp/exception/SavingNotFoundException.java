package pl.disciplineapp.DisciplineApp.exception;

public class SavingNotFoundException extends RuntimeException {
    public SavingNotFoundException(String message) {
        super(message);
    }
}
