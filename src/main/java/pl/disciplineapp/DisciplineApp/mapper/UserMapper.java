package pl.disciplineapp.DisciplineApp.mapper;

import pl.disciplineapp.DisciplineApp.dto.request.UserRegistrationRequest;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.model.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getEnabled(),
                user.getIsSubscriber()
        );
    }

    public static User toUserModel(UserRegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .enabled(false)
                .isSubscriber(false)
                .build();
    }
}
