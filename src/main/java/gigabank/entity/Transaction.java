package gigabank.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Информация о совершенной банковской транзакции
 */
@Entity
@Table(name = "app_transaction")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "transaction_value")
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TransactionCategory category;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @CreationTimestamp
    private LocalDateTime createdDate;
}