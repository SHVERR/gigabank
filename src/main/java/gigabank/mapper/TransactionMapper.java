package gigabank.mapper;

import gigabank.dto.TransactionDTO;
import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.TransactionCategory;
import gigabank.entity.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getValue(),
                transaction.getType().getId(),
                transaction.getCategory().getId(),
                transaction.getBankAccount().getId(),
                transaction.getCreatedDate()
        );
    }

    public Transaction toEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction(
                transactionDTO.getId(),
                transactionDTO.getValue(),
                new TransactionType(),
                new TransactionCategory(),
                new BankAccount(),
                transactionDTO.getCreatedDate());

        transaction.getType().setId(transactionDTO.getTypeId());
        transaction.getCategory().setId(transactionDTO.getCategoryId());
        transaction.getBankAccount().setId(transactionDTO.getBankAccountId());

        return transaction;
    }
}