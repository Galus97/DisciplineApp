package pl.disciplineapp.DisciplineApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min = 3)
    private String firstName;

    @Size(min = 3)
    private String lastName;

    @Size(min = 5)
    @Email
    @Column(unique = true)
    private String email;

    @Size(min = 5)
    private String password;

    private Boolean enabled;

    private Boolean isSubscriber;
}
