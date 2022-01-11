package budget.spend_analysis;

import budget.category.PurchaseCategory;
import budget.transaction.Transaction;
import budget.transaction.TransactionService;
import budget.utils.Amount;

import java.util.List;

public class SortAllPurchasesSpendAnalysis implements SpendAnalysis {
    private final TransactionService service;

    public SortAllPurchasesSpendAnalysis(TransactionService service) {
        this.service = service;
    }

    @Override
    public void analyse() {
        // Get purchase transactions
        var purchases = service.getPurchases();

        if (purchases.size() == 0) {
            displayEmptyList();
            return;
        }

        // Sort list
        var sortedList = service.sortTransactions(purchases);
        displayPurchaseList(sortedList);
    }

    private void displayPurchaseList(List<Transaction> purchases) {
        System.out.println(PurchaseCategory.ALL.getDisplayName() + ":");

        if (purchases.size() > 0) {
            displayPurchases(purchases);
        } else {
            displayEmptyList();
        }
    }

    private void displayPurchases(List<Transaction> purchases) {
        purchases.forEach(System.out::println);
        var totalAmount = new Amount(service.getTotal(purchases));
        System.out.println("Total: " + totalAmount);
    }

    private void displayEmptyList() {
        System.out.println("The purchase list is empty!");
    }
}
