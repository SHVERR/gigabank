package gigabank.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomAnalyticsRepository {
    List<BigDecimal> getTransactionValuesByBankAccountIdAndRangeDates(Long bankAccountId,
                                                                    LocalDateTime startDate,
                                                                    LocalDateTime endDate);
}