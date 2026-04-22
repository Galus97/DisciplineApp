package pl.disciplineapp.DisciplineApp.dto.response;

import pl.disciplineapp.DisciplineApp.entity.User;

public record UserResponse(Long userId, String firstName, String lastName, String email, String password,
                           Boolean enabled, Boolean isSubscriber ) {

}
