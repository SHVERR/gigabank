package gigabank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccountDTO {
    private Long id;
    private BigDecimal balance;
    private Long ownerId;
}