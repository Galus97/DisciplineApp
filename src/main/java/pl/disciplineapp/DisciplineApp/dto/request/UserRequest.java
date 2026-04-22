package pl.disciplineapp.DisciplineApp.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequest {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private Boolean isSubscriber;
}
