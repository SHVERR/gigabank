package gigabank.dto;

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

}