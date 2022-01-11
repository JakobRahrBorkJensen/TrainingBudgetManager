package budget.transaction;

import budget.category.PurchaseCategory;
import budget.utils.Amount;

/**
 * This is a POJO class for a transaction
 */
public class Transaction {
    private final String productName;
    private final Amount transactionAmount;
    private final TransactionType type;
    private final PurchaseCategory category;

    private Transaction(Builder builder) {
        this.productName = builder.productName;
        this.transactionAmount = builder.transactionAmount;
        this.type = builder.type;
        this.category = builder.category;
    }

    public String getProductName() {
        return productName;
    }

    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public TransactionType getType() {
        return type;
    }

    public PurchaseCategory getCategory() {
        return category;
    }

    /**
     * ToString method is designed to align with required format for printing each transaction
     */
    @Override
    public String toString() {
        return productName + " " + transactionAmount;
    }

    // Builder pattern
    public static class Builder {
        private String productName;
        private Amount transactionAmount;
        private TransactionType type;
        private PurchaseCategory category;

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder transactionAmount(Amount transactionAmount) {
            this.transactionAmount = transactionAmount;
            return this;
        }

        public Builder type(TransactionType type) {
            this.type = type;
            return this;
        }

        public Builder category(PurchaseCategory category) {
            this.category = category;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
