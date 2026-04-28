package pl.disciplineapp.DisciplineApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.component.RegisterValidator;
import pl.disciplineapp.DisciplineApp.dto.request.UserRequest;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.entity.User;
import pl.disciplineapp.DisciplineApp.exception.UserNotFoundException;
import pl.disciplineapp.DisciplineApp.exception.ValidationException;
import pl.disciplineapp.DisciplineApp.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MessageService messageService;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidator registerValidator;

    public UserResponse getUserResponse(Long userId) {
        throwIfIdIsNotValid(userId);
        return UserResponse.fromEntity(getUserOrThrowIfNotExist(userId));
    }

    public void deleteUser(Long userId) {
        throwIfIdIsNotValid(userId);
        userRepository.delete(getUserOrThrowIfNotExist(userId));
    }

    public UserResponse saveNewUser(UserRequest userRequest) throws ValidationException {
        throwIfRequestIsNull(userRequest);
        User user = buildUser(userRequest);
        if(registerValidator.validateUser(user).isEmpty()){
            return UserResponse.fromEntity(buildUser(userRequest));
        } else {
            throw new ValidationException(registerValidator.validateUser(user));
        }
    }

        public UserResponse updateUser(UserRequest userRequest) {
        throwIfRequestIsNull(userRequest);
        throwIfIdIsNotValid(userRequest.getUserId());

        User existingUser = getUserOrThrowIfNotExist(userRequest.getUserId());
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());

        existingUser.setPassword(userRequest.getPassword());
    }

    private User buildUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .enabled(userRequest.getEnabled())
                .isSubscriber(userRequest.getIsSubscriber())
                .build();
    }

    private void throwIfRequestIsNull(UserRequest userRequest) {
        if (userRequest == null) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.USER_REQUEST_IS_NULL));
        }
    }

    private User getUserOrThrowIfNotExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOT_FOUND)));
    }

    private void throwIfIdIsNotValid(Long userId) {
        if (userId == null || userId <= 0) {
            throw new UserNotFoundException(messageService.getMessage(ErrorMessages.INVALID_USER_ID));
        }
    }
}
