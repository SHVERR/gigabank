package gigabank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Информация о пользователе
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    @EqualsAndHashCode.Exclude
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", bankAccounts=" + bankAccounts +
                '}';
    }
}