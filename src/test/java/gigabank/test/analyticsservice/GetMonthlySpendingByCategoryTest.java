package gigabank.test.analyticsservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static gigabank.test.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GetMonthlySpendingByCategoryTest {
    @BeforeEach
    void initializerTest() {
        initializer();
    }

    @BeforeEach
    void resetBankAccountBalance() {
        bankAccountTest1.setBalance(BigDecimal.ZERO);
    }

    @Test
    void mustGetSumLastMonthTransactionsByBeautyCategory() {
        BigDecimal sum = analyticsService.getMonthlySpendingByCategory(bankAccountTest1, BEAUTY_CATEGORY);
        assertEquals(TEN_DOLLARS, sum);
    }

    @Test
    void hasNoTransactionsForTheLastMonth() {
        BigDecimal sum = analyticsService.getMonthlySpendingByCategory(bankAccountTest3, BEAUTY_CATEGORY);
        assertEquals(BigDecimal.ZERO, sum);
    }

    @Test
    void getZeroIfBankAccountIsNull() {
        BigDecimal sum = analyticsService.getMonthlySpendingByCategory(bankAccountNull, CATEGORY_NULL);
        assertEquals(BigDecimal.ZERO, sum);
    }

    @Test
    void getZeroIfCategoryIsNull() {
        BigDecimal sum = analyticsService.getMonthlySpendingByCategory(bankAccountTest3, CATEGORY_NULL);
        assertEquals(BigDecimal.ZERO, sum);
    }
}