package gigabank.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class Utils {
    public static final boolean IS_SUCCESS = true;
    static Random random = new Random();

    public static String generateUserId() {
        int randomId = random.nextInt(1, 10000+1);
        return String.valueOf(randomId);
    }

    public static String generateBankAccountId() {
        int randomId = random.nextInt(1, 100000+1);
        return String.valueOf(randomId);
    }

    public static String generateTransactionId() {
        int randomId = random.nextInt(1, 100000000+1);
        return String.valueOf(randomId);
    }

    public static final LocalDate UNKNOWN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDateTime UNKNOWN_DATE_TIME = LocalDateTime.of(
            1, 1, 1, 1, 1, 1);



}
