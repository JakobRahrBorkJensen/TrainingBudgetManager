package budget.sort;

import budget.transaction.Transaction;

import java.util.Comparator;

public class TransactionSort implements Comparator<Transaction> {
    @Override
    public int compare(Transaction a, Transaction b) {
        return Double.compare(
                b.getTransactionAmount().getAmount(),
                a.getTransactionAmount().getAmount()
        );
    }
}
