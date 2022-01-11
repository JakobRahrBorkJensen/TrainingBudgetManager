package budget.spend_analysis;

import budget.category.PurchaseCategory;
import budget.transaction.Transaction;
import budget.transaction.TransactionService;
import budget.utils.Amount;

import java.util.List;
import java.util.Scanner;

import static budget.utils.ReceiveInputUtils.convertToPurchaseCategory;
import static budget.utils.ReceiveInputUtils.receiveMenuInput;

public class SortCertainTypeSpendAnalysis implements SpendAnalysis {
    private final TransactionService service;
    private final Scanner scanner;

    public SortCertainTypeSpendAnalysis(TransactionService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void analyse() {
        // Get purchase transactions
        var purchases = service.getPurchases();

        // Choose subset of purchases
        int action = chooseTypeOfPurchase();

        var category = convertToPurchaseCategory(action);
        if (category == PurchaseCategory.ALL) {
            throw new IllegalArgumentException("'All' is not a valid subset");
        }

        purchases = service.getFilteredTransactionsByCategory(purchases, category);

        // Sort list
        var sortedList = service.sortTransactions(purchases);
        displayPurchaseList(sortedList, category);
    }

    private int chooseTypeOfPurchase() {
        showTypes();
        return receiveMenuInput(scanner);
    }

    /**
     * Shows the menu of puchase types.
     * <p>
     * Be aware, this method must be kept in sync with
     * {@link budget.utils.ReceiveInputUtils#convertToPurchaseCategory(int)}
     */
    private static void showTypes() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
    }

    private void displayPurchaseList(List<Transaction> purchases, PurchaseCategory category) {
        System.out.println(category.getDisplayName() + ":");

        if (purchases.size() > 0) {
            displayPurchases(purchases);
        } else {
            displayEmptyList();
        }
    }

    private void displayPurchases(List<Transaction> purchases) {
        purchases.forEach(System.out::println);
        var totalAmount = new Amount(service.getTotal(purchases));
        System.out.println("Total sum: " + totalAmount);
    }

    private void displayEmptyList() {
        System.out.println("The purchase list is empty!");
    }
}
