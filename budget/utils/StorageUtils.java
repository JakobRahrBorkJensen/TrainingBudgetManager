package budget.utils;

import budget.category.PurchaseCategory;
import budget.transaction.Transaction;
import budget.transaction.TransactionType;

/**
 * This class contains methods for storage of transactions
 */
public class StorageUtils {
    private static final String SEPARATOR = ";";

    /**
     * Serializes object into a string.
     * <p>
     * NB: In real project, use a library like Jackson instead.
     */
    public static String serializeToStorage(Transaction transaction) {
        return transaction.getProductName() + SEPARATOR +
                transaction.getTransactionAmount() + SEPARATOR +
                transaction.getType() + SEPARATOR +
                transaction.getCategory();
    }

    /**
     * Deserializes string into a Transaction object.
     * <p>
     * NB: In real project, use a library like Jackson instead.
     */
    public static Transaction deserializeFromStorage(String storageString) {
        var attributes = storageString.split(SEPARATOR);

        var productName = attributes[0];

        // TransactionAmount prep
        var preppedAmountAsString = attributes[1].substring(1).replace(",", "");
        var transactionAmount = new Amount(Double.parseDouble(preppedAmountAsString));

        var type = TransactionType.valueOf(attributes[2]);

        var category = !attributes[3].equals("null") ? PurchaseCategory.valueOf(attributes[3]) : null;

        return new Transaction.Builder()
                .productName(productName)
                .transactionAmount(transactionAmount)
                .type(type)
                .category(category)
                .build();
    }
}
