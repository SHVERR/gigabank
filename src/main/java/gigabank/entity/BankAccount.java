package gigabank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gigabank.dto.BankAccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Информация о банковском счете пользователя
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccount {
    private long id;
    private BigDecimal balance;
    @EqualsAndHashCode.Exclude
    private User owner;
    @EqualsAndHashCode.Exclude
    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                ", transactions=" + transactions +
                '}';
    }
}

