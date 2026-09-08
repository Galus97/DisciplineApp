package pl.disciplineapp.DisciplineApp.dto.response;

public record UserResponse(
        Long userId,
        String firstName,
        String lastName,
        String email,
        String password,
        Boolean enabled,
        Boolean isSubscriber ) {
}
