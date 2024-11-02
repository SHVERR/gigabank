package gigabank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

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
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
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
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", bankAccounts=" + bankAccounts +
                '}';
    }
}