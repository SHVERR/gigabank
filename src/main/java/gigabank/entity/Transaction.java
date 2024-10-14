package gigabank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Информация о совершенной банковской транзакции
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Transaction {
    private long id;
    private BigDecimal value;
    private TransactionType type;
    private TransactionCategory category;
    @EqualsAndHashCode.Exclude
    private BankAccount bankAccount;
    private LocalDateTime createdDate;

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", bankAccount" + bankAccount +
                ", createdDate=" + createdDate +
                '}';
    }
}