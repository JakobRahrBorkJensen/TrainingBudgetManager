package budget.operations;

import budget.category.PurchaseCategory;
import budget.transaction.Transaction;
import budget.transaction.TransactionService;
import budget.utils.Amount;

import java.util.List;
import java.util.Scanner;

import static budget.utils.ReceiveInputUtils.convertToPurchaseCategory;
import static budget.utils.ReceiveInputUtils.receiveMenuInput;

/**
 * Handle menu option "Show List of Purchases".
 * Structured so that this class handles the UI (console), while business logic is placed in {@link TransactionService}
 */
public class ShowListOfPurchasesOperation implements Operation {
    private final Scanner scanner;
    private final TransactionService service;

    public ShowListOfPurchasesOperation(Scanner scanner, TransactionService service) {
        this.scanner = scanner;
        this.service = service;
    }

    @Override
    public void doOperation() {
        while (true) {
            // Get purchase transactions
            var purchases = service.getPurchases();

            if (purchases.size() == 0) {
                displayEmptyList();
                return;
            }

            // Choose subset of purchases
            int action = chooseTypeOfPurchase();
            if (action == 6) {
                return;
            }

            var category = convertToPurchaseCategory(action);
            if (category != PurchaseCategory.ALL) {
                purchases = service.getFilteredTransactionsByCategory(purchases, category);
            }

            // Display purchases
            System.out.println(category.getDisplayName() + ":");

            if (purchases.size() > 0) {
                displayPurchasesList(purchases);
            } else {
                displayEmptyList();
            }
            System.out.println();
        }
    }

    private void displayPurchasesList(List<Transaction> purchases) {
        purchases.forEach(System.out::println);
        var totalAmount = new Amount(service.getTotal(purchases));
        System.out.println("Total sum: " + totalAmount);
    }

    private void displayEmptyList() {
        System.out.println("The purchase list is empty!");
    }

    private int chooseTypeOfPurchase() {
        showTypes();
        return receiveMenuInput(scanner);
    }

    /**
     * Shows the menu of puchase types.
     *
     * Be aware, this method must be kept in sync with
     * {@link budget.utils.ReceiveInputUtils#convertToPurchaseCategory(int)}
     */
    private static void showTypes() {
        System.out.println("Choose the type of purchases");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
    }
}
