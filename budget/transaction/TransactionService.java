package budget.transaction;

import budget.category.PurchaseCategory;
import budget.sort.TransactionSort;
import budget.utils.Amount;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service class contains all business logic related to transactions
 */
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService() {
        repository = new TransactionRepository();
    }

    /**
     * Returns all transactions
     */
    public List<Transaction> getTransactions() {
        return repository.getTransactions();
    }

    /**
     * Overwrites the list of transactions with a new list
     */
    public void overwriteTransactions(List<Transaction> newTransactions) {
        repository.setTransactions(newTransactions);
    }

    /**
     * Returns all purchase transactions
     */
    public List<Transaction> getPurchases() {
        return repository.getTransactions().stream()
                .filter(transaction -> TransactionType.PURCHASE == transaction.getType())
                .collect(Collectors.toList());
    }

    /**
     * Returns all income transactions
     */
    public List<Transaction> getIncomes() {
        return repository.getTransactions().stream()
                .filter(transaction -> TransactionType.INCOME == transaction.getType())
                .collect(Collectors.toList());
    }

    /**
     * Calculates the sum of a list of transactions (transaction amounts).
     * The balance cannot be negative. If so, it will be set to 0.
     */
    public Amount getBalance() {
        var income = getTotal(getIncomes());
        var expenses = getTotal(getPurchases());
        var sumAmount = income - expenses;

        return sumAmount > 0 ? new Amount(sumAmount) : new Amount(0);
    }

    /**
     * Adds provided transaction to the repository of transactions
     */
    public void addTransaction(Transaction transaction) {
        repository.addTransaction(transaction);
    }

    /**
     * Calculates the total amount of a list of transactions.
     */
    public double getTotal(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getTransactionAmount)
                .mapToDouble(Amount::getAmount)
                .sum();
    }

    public List<Transaction> getFilteredTransactionsByCategory(List<Transaction> transactions, PurchaseCategory category) {
        return transactions.stream()
                .filter(purchase -> category == purchase.getCategory())
                .collect(Collectors.toList());
    }

    public List<Transaction> sortTransactions(List<Transaction> transactions) {
        List<Transaction> sortedList = new ArrayList<>(transactions);
        sortedList.sort(new TransactionSort());
        return sortedList;
    }
}
