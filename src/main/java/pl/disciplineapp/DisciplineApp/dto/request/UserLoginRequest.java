package pl.disciplineapp.DisciplineApp.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Data
@Getter
public class UserLoginRequest {
    @Length(min = 5)
    @Email
    private String email;

    @Length(min = 5)
    private String password;
}
