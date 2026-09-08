package pl.disciplineapp.DisciplineApp.mapper;

import pl.disciplineapp.DisciplineApp.dto.request.UserRequest;
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

    public static User toUserModel(UserRequest userRequest) {
        return User.builder()
                .userId(userRequest.getUserId())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .enabled(userRequest.getEnabled())
                .isSubscriber(userRequest.getIsSubscriber())
                .build();
    }
}
