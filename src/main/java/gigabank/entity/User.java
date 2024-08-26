package gigabank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Информация о пользователе
 */
@Component
@Data
@AllArgsConstructor
public class User {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    @EqualsAndHashCode.Exclude
    private List<BankAccount> bankAccounts;

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