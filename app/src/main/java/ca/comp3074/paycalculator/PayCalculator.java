package ca.comp3074.paycalculator;

import java.math.BigDecimal;

/** Pure calculation logic; currency is rounded only when displayed. */
public final class PayCalculator {
    private PayCalculator() {}
    public static BigDecimal[] calculate(BigDecimal hours, BigDecimal rate, BigDecimal taxPercent) {
        if (hours.signum() < 0 || rate.signum() < 0 || taxPercent.signum() < 0
                || taxPercent.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Inputs must be nonnegative and tax must be 0 to 100.");
        }
        BigDecimal regularHours = hours.min(new BigDecimal("40"));
        BigDecimal pay = regularHours.multiply(rate);
        BigDecimal overtime = hours.subtract(regularHours).multiply(rate).multiply(new BigDecimal("1.5"));
        // Assignment explicitly specifies tax on regular pay, excluding overtime.
        BigDecimal tax = pay.multiply(taxPercent.movePointLeft(2));
        return new BigDecimal[] {pay, overtime, pay.add(overtime), tax};
    }
}
