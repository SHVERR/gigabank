package gigabank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccountDTO {
    private long id;
    private BigDecimal balance;
    private long ownerId;
    private List<Long> transactionsIds = new ArrayList<>();
}