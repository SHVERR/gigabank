package gigabank.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransactionCategory {
    private long id;
    private String name;
}