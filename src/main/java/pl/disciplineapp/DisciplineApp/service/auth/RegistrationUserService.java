package pl.disciplineapp.DisciplineApp.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.disciplineapp.DisciplineApp.component.ErrorMessages;
import pl.disciplineapp.DisciplineApp.dto.request.UserRegistrationRequest;
import pl.disciplineapp.DisciplineApp.dto.response.UserResponse;
import pl.disciplineapp.DisciplineApp.exception.ValidationException;
import pl.disciplineapp.DisciplineApp.mapper.UserMapper;
import pl.disciplineapp.DisciplineApp.model.User;
import pl.disciplineapp.DisciplineApp.repository.UserRepository;
import pl.disciplineapp.DisciplineApp.util.RegisterValidator;
import pl.disciplineapp.DisciplineApp.util.ServiceValidator;

@Service
@RequiredArgsConstructor
public class RegistrationUserService {
    private final UserRepository userRepository;
    private final RegisterValidator registerValidator;
    private final ServiceValidator serviceValidator;

    @Transactional
    public UserResponse saveNewUser(UserRegistrationRequest userRequest) throws ValidationException {
        serviceValidator.throwIfRequestIsNull(userRequest, ErrorMessages.USER_REQUEST_IS_NULL);

        if(registerValidator.validateUser(userRequest).isEmpty()){
            User user = UserMapper.toUserModel(userRequest);
            return UserMapper.toUserResponse(userRepository.save(user));
        } else {
            throw new ValidationException(registerValidator.validateUser(userRequest));
        }
    }
}
