package library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {
    public static final int LOAN_DAYS = 14;
    public static final int FINE_PER_DAY = 2;

    public static int calculate(LocalDate issued, LocalDate returned) {
        long days = ChronoUnit.DAYS.between(issued, returned);
        return (int) Math.max(0, days - LOAN_DAYS) * FINE_PER_DAY;
    }
}
