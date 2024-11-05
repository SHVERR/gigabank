package gigabank.mapper;

import gigabank.dto.BankAccountDTO;
import gigabank.entity.BankAccount;
import gigabank.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccountDTO toDTO(BankAccount bankAccount) {
        return new BankAccountDTO(
                bankAccount.getId(),
                bankAccount.getBalance(),
                bankAccount.getOwner().getId());
    }

    public BankAccount toEntity(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount(
                bankAccountDTO.getId(),
                bankAccountDTO.getBalance(),
                new User(),
                null);

        bankAccount.getOwner().setId(bankAccountDTO.getOwnerId());

        return bankAccount;
    }
}