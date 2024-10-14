package gigabank.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class Utils {
    public static final boolean IS_SUCCESS = true;
    static Random random = new Random();

    public static long generateUserId() {
        return random.nextInt(1, 10000+1);
    }

    public static long generateBankAccountId() {
        return random.nextInt(1, 100000+1);
    }

    public static long generateTransactionId() {
        return random.nextInt(1, 100000000+1);
    }

    public static final LocalDate UNKNOWN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDateTime UNKNOWN_DATE_TIME = LocalDateTime.of(
            1, 1, 1, 1, 1, 1);
}
