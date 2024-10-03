package gigabank.dto;

import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.TransactionCategory;
import gigabank.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionDTO {
    private long id;
    private BigDecimal value;
    private long typeId;
    private long categoryId;
    private long bankAccountId;
    private LocalDateTime createdDate;

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