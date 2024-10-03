package gigabank.dto;

import gigabank.entity.BankAccount;
import gigabank.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {
    private long id;
    @NotBlank
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private List<Long> bankAccountsIds = new ArrayList<>();

    public UserDTO toDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.birthDate = user.getBirthDate();
        this.bankAccountsIds = user.getBankAccounts().stream()
                .map(BankAccount::getId)
                .toList();
        return this;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setBirthDate(userDTO.getBirthDate());
        user.setBankAccounts(new ArrayList<>());

/*
        user.setBankAccounts(userDTO.getBankAccountsIds().stream()
                .map(bankAccountId -> new BankAccount(
                        bankAccountId,
                        new BigDecimal("0.00"),
                        user,
                        new ArrayList<>()))
                .toList());
*/

        return user;
    }
}