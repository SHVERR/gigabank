package gigabank.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CustomAnalyticsRepositoryImpl implements CustomAnalyticsRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BigDecimal> getTransactionValuesByBankAccountIdAndRangeDates(Long bankAccountId,
                                                                            LocalDateTime startDate,
                                                                            LocalDateTime endDate) {

        return em.createQuery("SELECT t.value FROM Transaction t " +
                        "WHERE t.bankAccount.id = :bankAccountId AND t.createdDate BETWEEN :startDate AND :endDate", BigDecimal.class)
                .setParameter("bankAccountId", bankAccountId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}