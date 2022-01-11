package budget.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as an in-memory database for the application.
 */
class TransactionRepository {
    private final List<Transaction> transactions;

    TransactionRepository() {
        transactions = new ArrayList<>();
    }

    List<Transaction> getTransactions() {
        return transactions;
    }

    void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    void setTransactions(List<Transaction> transactionList) {
        transactions.clear();
        transactions.addAll(transactionList);
    }
}
