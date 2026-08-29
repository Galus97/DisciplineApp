package pl.disciplineapp.DisciplineApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequest {
    private Long userId;
    @Size(min = 3)
    private String firstName;
    @Size(min = 3)
    private String lastName;
    @Email
    @Size(min = 5)
    private String email;
    @Size(min = 5)
    private String password;
    private Boolean enabled;
    private Boolean isSubscriber;
}
