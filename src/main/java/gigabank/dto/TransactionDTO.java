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
    private Long id;
    private BigDecimal value;
    private Long typeId;
    private Long categoryId;
    private Long bankAccountId;
    private LocalDateTime createdDate;

}