package pl.disciplineapp.DisciplineApp.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.component.MessageService;
import pl.disciplineapp.DisciplineApp.dto.request.UserRegistrationRequest;
import pl.disciplineapp.DisciplineApp.dto.request.UserRequest;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.mapper.UserMapper;
import pl.disciplineapp.DisciplineApp.model.User;
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
    private final ServiceValidator serviceValidator;

    @Transactional(readOnly = true)
    public UserResponse getUserResponse(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        return UserMapper.toUserResponse(getUserOrThrowIfNotExist(userId));
    }

    @Transactional
    public void deleteUser(Long userId) {
        serviceValidator.throwIfIdIsNotValid(userId, ErrorMessages.INVALID_USER_ID);
        userRepository.delete(getUserOrThrowIfNotExist(userId));
    }

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        serviceValidator.throwIfRequestIsNull(userRequest, ErrorMessages.USER_REQUEST_IS_NULL);
        serviceValidator.throwIfIdIsNotValid(userRequest.getUserId(), ErrorMessages.INVALID_USER_ID);

        User existingUser = getUserOrThrowIfNotExist(userRequest.getUserId());
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setEmail(userRequest.getEmail());

        if(userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        return UserMapper.toUserResponse(userRepository.save(existingUser));
    }

    //Using this method in others Services
    public User getUserOrThrowIfNotExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOT_FOUND, userId)));
    }
}
