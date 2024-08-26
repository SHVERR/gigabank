package gigabank.test;

import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.TransactionType;
import gigabank.entity.User;
import gigabank.service.AnalyticsService;
import gigabank.service.BankAccountService;
import gigabank.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static gigabank.service.Utils.generateTransactionId;

public class TestUtils {
    public static BankAccountService bankAccountService = new BankAccountService();
    public static TransactionService transactionService = new TransactionService();
    public static AnalyticsService analyticsService = new AnalyticsService();

    public static final BigDecimal TEN_DOLLARS = new BigDecimal("10.00");
    public static final BigDecimal FIFTEEN_DOLLARS = new BigDecimal("15.00");
    public static final BigDecimal TWENTY_DOLLARS = new BigDecimal("20.00");
    public static final BigDecimal THIRTY_DOLLARS = new BigDecimal("30.00");

    public static final String BEAUTY_CATEGORY = "Beauty";
    public static final String HEALTH_CATEGORY = "Health";
    public static final String EDUCATION_CATEGORY = "Education";
    public static final String TRANSFER_CATEGORY = "Transfer";
    public static final String CATEGORY_NULL = null;

    public static final LocalDateTime TEN_DAYS_AGO = LocalDateTime.now().minusDays(10);
    public static final LocalDateTime FIVE_MONTHS_AGO = LocalDateTime.now().minusMonths(5);
    public static final LocalDateTime THREE_DAYS_AGO = LocalDateTime.now().minusDays(3);

    public static User userIvan = new User(
            "1",
            "Ivan",
            "Ivanovich",
            "Ivanov",
            LocalDate.of(1963, 7, 19),
            new ArrayList<>());

    public static User userMaria = new User(
            "2",
            "Maria",
            "Petrovna",
            "Sokolova",
            LocalDate.of(1984, 11, 1),
            new ArrayList<>());

    public static User userNull = null;

    public static BankAccount bankAccountTest1 = new BankAccount("1", BigDecimal.ZERO, userIvan, new ArrayList<>());
    public static BankAccount bankAccountTest2 = new BankAccount("2", BigDecimal.ZERO, userIvan, new ArrayList<>());
    public static BankAccount bankAccountTest3 = new BankAccount("3", BigDecimal.ZERO, userMaria, new ArrayList<>());
    public static BankAccount bankAccountNull;

    public static Transaction transaction1 = new Transaction(
            "1",
            TEN_DOLLARS,
            TransactionType.PAYMENT,
            BEAUTY_CATEGORY,
            bankAccountTest1,
            TEN_DAYS_AGO);

    public static Transaction transaction2 = new Transaction(
            "2",
            FIFTEEN_DOLLARS,
            TransactionType.PAYMENT,
            BEAUTY_CATEGORY,
            bankAccountTest1,
            FIVE_MONTHS_AGO);

    public static Transaction transaction3 = new Transaction(
            "3",
            TWENTY_DOLLARS,
            TransactionType.PAYMENT,
            HEALTH_CATEGORY,
            bankAccountTest2,
            THREE_DAYS_AGO);

    public static Transaction transaction4 = new Transaction(
            "4",
            TWENTY_DOLLARS,
            TransactionType.PAYMENT,
            EDUCATION_CATEGORY,
            bankAccountTest3,
            FIVE_MONTHS_AGO);

    public static Transaction transaction5 = new Transaction(
            "5",
            TWENTY_DOLLARS,
            TransactionType.TRANSFER,
            TRANSFER_CATEGORY,
            bankAccountTest3,
            FIVE_MONTHS_AGO);

    public static void initializer() {
        userIvan.getBankAccounts().clear();
        userIvan.getBankAccounts().add(bankAccountTest1);
        userIvan.getBankAccounts().add(bankAccountTest2);
        bankAccountTest1.getTransactions().clear();
        bankAccountTest1.getTransactions().add(transaction1);
        bankAccountTest1.getTransactions().add(transaction2);
        bankAccountTest2.getTransactions().clear();
        bankAccountTest2.getTransactions().add(transaction3);

        userMaria.getBankAccounts().clear();
        userMaria.getBankAccounts().add(bankAccountTest3);
        bankAccountTest3.getTransactions().clear();
        bankAccountTest3.getTransactions().add(transaction4);
        bankAccountTest3.getTransactions().add(transaction5);
    }

    //--------------------------------------------------------------------

    /**
     * Создаёт транзакцию со случайной суммой, а также дефолтные объекты BankAccount и User
     * для каждой новой транзакции
     */
    public static List<Transaction> generateTransactions(int n) {
        List<Transaction> transactions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i <= n; i++) {
            int randomValue = random.nextInt(1, 10000 + 1);
            String value = randomValue + ".00";
            Transaction transaction = new Transaction(
                    generateTransactionId(),
                    new BigDecimal(value),
                    TransactionType.PAYMENT,
                    BEAUTY_CATEGORY,
                    bankAccountTest1,
                    TEN_DAYS_AGO);

            transactions.add(transaction);
        }

        return transactions;
    }
}