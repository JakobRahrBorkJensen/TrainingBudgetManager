package budget.spend_analysis;

import budget.category.Category;
import budget.category.PurchaseCategory;
import budget.sort.CategorySort;
import budget.transaction.TransactionService;
import budget.utils.Amount;

import java.util.ArrayList;
import java.util.List;

/**
 * Spend Analyis sorting by type.
 */
public class SortByTypeSpendAnalysis implements SpendAnalysis {
    private final TransactionService service;

    public SortByTypeSpendAnalysis(TransactionService service) {
        this.service = service;
    }

    @Override
    public void analyse() {
        // Get purchase transactions
        var purchases = service.getPurchases();

        // Prep types
        List<Category> types = new ArrayList<>();
        var categories = PurchaseCategory.values();
        for (int i = 0; i < categories.length - 1; i++) { // Go through all other than All (last)
            var purchasesOfType = service.getFilteredTransactionsByCategory(purchases, categories[i]);
            var sum = service.getTotal(purchasesOfType);

            types.add(new Category.Builder()
                    .category(categories[i])
                    .sumAmount(new Amount(sum))
                    .build()
            );
        }

        // Sort list
        var sortedList = sortCategories(types);
        displayTypeList(sortedList);
    }

    private List<Category> sortCategories(List<Category> types) {
        List<Category> sortedList = new ArrayList<>(types);
        sortedList.sort(new CategorySort());
        return sortedList;
    }

    private void displayTypeList(List<Category> types) {
        // Get sum
        var sum = types.stream()
                .map(Category::getSumAmount)
                .mapToDouble(Amount::getAmount)
                .sum();

        // Print
        System.out.println("Types:");
        types.forEach(System.out::println);
        System.out.println("Total sum: " + new Amount(sum));
    }
}
