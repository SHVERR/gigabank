package gigabank.repository;

import gigabank.dto.TransactionAggregationRequestDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CustomAnalyticsRepository {
    List<BigDecimal> aggregateTransactions(TransactionAggregationRequestDTO request);
}