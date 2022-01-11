package budget.utils;

import java.util.Locale;

/**
 * This is a POJO class for an amount
 */
public class Amount {
    private final String currency = "$";
    private final double amount;

    public Amount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return currency + String.format(Locale.US, "%,.2f", amount);
    }
}
