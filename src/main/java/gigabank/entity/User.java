package gigabank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Информация о пользователе
 */
@Entity
@Table(name = "app_user")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;
}