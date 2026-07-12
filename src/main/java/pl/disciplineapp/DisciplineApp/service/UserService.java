package pl.disciplineapp.DisciplineApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.UserRequest;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.entity.User;
import pl.disciplineapp.DisciplineApp.exception.UserNotFoundException;
import pl.disciplineapp.DisciplineApp.exception.ValidationException;
import pl.disciplineapp.DisciplineApp.repository.UserRepository;
import pl.disciplineapp.DisciplineApp.util.RegisterValidator;
import pl.disciplineapp.DisciplineApp.util.ServiceValidator;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MessageService messageService;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidator registerValidator;
    private final ServiceValidator serviceValidator;

    public UserResponse getUserResponse(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return UserResponse.fromEntity(getUserOrThrowIfNotExist(userId));
    }

    @Transactional
    public UserResponse saveNewUser(UserRequest userRequest) throws ValidationException {
        serviceValidator.throwIfRequestIsNull(userRequest, ErrorMessages.USER_NOT_FOUND);
        User user = buildUser(userRequest);
        if(registerValidator.validateUser(user).isEmpty()){
            return UserResponse.fromEntity(userRepository.save(buildUser(userRequest)));
        } else {
            throw new ValidationException(registerValidator.validateUser(user));
        }
    }

    @Transactional
    public void deleteUser(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        userRepository.delete(getUserOrThrowIfNotExist(userId));
    }

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        serviceValidator.throwIfRequestIsNull(userRequest, ErrorMessages.USER_NOT_FOUND);
        serviceValidator.throwIfIdIsNotValid(userRequest.getUserId(), ErrorMessages.INVALID_USER_ID);

        User existingUser = getUserOrThrowIfNotExist(userRequest.getUserId());
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());

        if(userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        return UserResponse.fromEntity(userRepository.save(existingUser));
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

    //Using this method in others Services
    public User getUserOrThrowIfNotExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOT_FOUND)));
    }
}
