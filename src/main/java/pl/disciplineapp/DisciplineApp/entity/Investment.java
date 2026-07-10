package pl.disciplineapp.DisciplineApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "investments")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long investmentId;

    @NotBlank
    private String investmentType;

    @NotNull
    private Float totalValue;

    @NotNull
    private Float quantity;

    @NotNull
    private Float unitPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
