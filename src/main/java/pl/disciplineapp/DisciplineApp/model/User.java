package pl.disciplineapp.DisciplineApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Builder
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE user_id = ?")
@SQLRestriction("is_deleted = false")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Length(min = 3)
    private String firstName;

    @Length(min = 3)
    private String lastName;

    @Length(min = 5)
    @Email
    @Column(unique = true)
    private String email;

    @Length(min = 5)
    private String password;

    private Boolean enabled;

    private Boolean isSubscriber;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;
}
