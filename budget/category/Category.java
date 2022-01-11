package budget.category;

import budget.transaction.Transaction;
import budget.utils.Amount;

public class Category {
    private final PurchaseCategory category;
    private final Amount sumAmount;

    private Category(Category.Builder builder) {
        this.category = builder.category;
        this.sumAmount = builder.sumAmount;
    }

    public Amount getSumAmount() {
        return sumAmount;
    }

    /**
     * ToString method is designed to align with required format for printing each category
     */
    @Override
    public String toString() {
        return category.getDisplayName() + " - " + sumAmount;
    }

    // Builder pattern
    public static class Builder {
        private PurchaseCategory category;
        private Amount sumAmount;

        public Category.Builder category(PurchaseCategory category) {
            this.category = category;
            return this;
        }

        public Category.Builder sumAmount(Amount sumAmount) {
            this.sumAmount = sumAmount;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
