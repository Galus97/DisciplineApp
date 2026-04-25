package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.dto.request.UserRequest;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.entity.User;
import pl.disciplineapp.DisciplineApp.exception.UserNotFoundException;
import pl.disciplineapp.DisciplineApp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUserResponse(Long userId) {
        throwIfIdIsNotValid(userId);
        return UserResponse.fromEntity(getUserOrThrowIfNotExist(userId));
    }

    public void deleteUser(Long userId) {
        throwIfIdIsNotValid(userId);
        userRepository.delete(getUserOrThrowIfNotExist(userId));
    }

    public UserResponse saveNewUser(UserRequest userRequest) {
        throwIfRequestIsNull(userRequest);
        return UserResponse.fromEntity(buildUser(userRequest));
    }

    private User buildUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .enabled(userRequest.getEnabled())
                .isSubscriber(userRequest.getIsSubscriber())
                .build();
    }

    private void throwIfRequestIsNull(UserRequest userRequest) {
        if (userRequest == null) {
            throw new IllegalArgumentException("UserRequest is null");
        }
    }

    private User getUserOrThrowIfNotExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
    }

    private void throwIfIdIsNotValid(Long userId) {
        if (userId == null || userId <= 0) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
    }
}
