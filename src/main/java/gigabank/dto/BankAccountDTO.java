package gigabank.dto;

import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccountDTO {
    private long id;
    private BigDecimal balance;
    private long ownerId;
    private List<Long> transactionsIds = new ArrayList<>();

    public BankAccountDTO toDTO(BankAccount bankAccount) {
        return new BankAccountDTO(
                bankAccount.getId(),
                bankAccount.getBalance(),
                bankAccount.getOwner().getId(),
                bankAccount.getTransactions().stream()
                        .map(Transaction::getId)
                        .toList()
        );
    }

    public BankAccount toEntity(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount(
                bankAccountDTO.getId(),
                bankAccountDTO.getBalance(),
                new User(),
                new ArrayList<>()
/*                bankAccountDTO.getTransactionsIds().stream()
                        .map(Transaction::new)
                        .toList()*/
        );

        bankAccount.getOwner().setId(bankAccountDTO.getOwnerId());

        return bankAccount;
    }
}