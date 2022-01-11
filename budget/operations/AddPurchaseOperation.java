package budget.operations;

import budget.exception.InvalidInputException;
import budget.category.PurchaseCategory;
import budget.transaction.Transaction;
import budget.transaction.TransactionService;
import budget.transaction.TransactionType;
import budget.utils.Amount;

import java.util.Arrays;
import java.util.Scanner;

import static budget.utils.ReceiveInputUtils.receiveMenuInput;

/**
 * Handle menu option "Add Purchase".
 * Structured so that this class handles the UI (console), while business logic is placed in {@link TransactionService}
 */
public class AddPurchaseOperation implements Operation {
    private final Scanner scanner;
    private final TransactionService service;

    public AddPurchaseOperation(Scanner scanner, TransactionService service) {
        this.scanner = scanner;
        this.service = service;
    }

    @Override
    public void doOperation() {
        while (true) {
            int action = chooseTypeOfPurchase();

            // Input 5 = back
            if (action == 5) {
                return;
            }

            var category = convertToPurchaseCategory(action);
            var purchaseTransaction = receivePurchaseTransaction(category);
            service.addTransaction(purchaseTransaction);

            System.out.println("Purchase was added!\n");
        }
    }

    /**
     * Method translates chosen action into corresponding purchase category.
     *
     * Be aware, the implementation is using a weak mapping by the Enum ordinal:
     * Benefit: If new enum values are added, this method do not need to be updated.
     * Drawback: Order in menu has to strictly match the ordinal.
     * Therefore, this method must be kept in sync with {@link #showTypes()}
     *
     * @param action option picked by user
     * @return Purchase category matching the action selected,
     * or throws {@link InvalidInputException} if action is not valid
     */
    private PurchaseCategory convertToPurchaseCategory(int action) {
        return Arrays.stream(PurchaseCategory.values())
                .filter(val -> val.ordinal() == action - 1)
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(action));
    }

    /**
     * Receives purchase transaction as raw inputs from command line.
     * Inputs will be converted into a purchase transaction
     */
    private Transaction receivePurchaseTransaction(PurchaseCategory category) {
        System.out.println("Enter purchase name:");
        String rawPurchaseNameInput = scanner.nextLine();

        System.out.println("Enter its price:");
        double amount = Double.parseDouble(scanner.nextLine());

        return new Transaction.Builder()
                .productName(rawPurchaseNameInput)
                .transactionAmount(new Amount(amount))
                .type(TransactionType.PURCHASE)
                .category(category)
                .build();
    }

    private int chooseTypeOfPurchase() {
        showTypes();
        return receiveMenuInput(scanner);
    }

    /**
     * Shows the menu of puchase types.
     *
     * Be aware, this method must be kept in sync with {@link #convertToPurchaseCategory(int)}
     */
    private static void showTypes() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
    }
}
