package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.User;

public record UserResponse(Long userId, String firstName, String lastName, String email, String password,
                           Boolean enabled, Boolean isSubscriber ) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                user.getIsSubscriber());
    }
}
