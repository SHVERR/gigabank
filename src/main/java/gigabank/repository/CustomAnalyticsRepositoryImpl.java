package gigabank.repository;

import gigabank.dto.TransactionAggregationRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.List;

public class CustomAnalyticsRepositoryImpl implements CustomAnalyticsRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BigDecimal> aggregateTransactions(TransactionAggregationRequestDTO request) {
        TypedQuery<BigDecimal> query = em.createQuery(
                "SELECT t.value FROM Transaction t " +
                        "WHERE t.bankAccount.id = :bankAccountId AND t.createdDate BETWEEN :startDate AND :endDate", BigDecimal.class);
        query.setParameter("bankAccountId", request.getBankAccountId());
        query.setParameter("startDate", request.getStartDate());
        query.setParameter("endDate", request.getEndDate());

        return query.getResultList();
    }
}