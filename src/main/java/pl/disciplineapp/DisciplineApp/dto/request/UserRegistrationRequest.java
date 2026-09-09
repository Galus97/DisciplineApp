package pl.disciplineapp.DisciplineApp.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
public class UserRegistrationRequest {
    @Length(min = 3)
    private String firstName;

    @Length(min = 3)
    private String lastName;

    @Length(min = 5)
    @Email
    private String email;

    @Length(min = 5)
    private String password;

    @Length(min = 5)
    private String repeatPassword;
}
