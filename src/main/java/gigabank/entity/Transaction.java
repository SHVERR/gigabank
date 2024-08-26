package gigabank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Информация о совершенной банковской транзакции
 */
@Data
@AllArgsConstructor
public class Transaction {
    private String id;
    private BigDecimal value;
    private TransactionType type;
    private String category;
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
                ", bankAccount id=" + bankAccount.getId() +
                ", createdDate=" + createdDate +
                '}';
    }
}