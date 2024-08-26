package gigabank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * Информация о банковском счете пользователя
 */
@Data
@AllArgsConstructor
public class BankAccount {
    private String id;
    private BigDecimal balance;
    @EqualsAndHashCode.Exclude
    private User owner;
    @EqualsAndHashCode.Exclude
    private List<Transaction> transactions;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", owner=" + "id:" + owner.getId() + " " + owner.getFirstName() + " " + owner.getLastName() +
                ", transactions=" + transactions +
                '}';
    }
}

