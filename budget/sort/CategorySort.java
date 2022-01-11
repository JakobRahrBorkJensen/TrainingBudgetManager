package budget.sort;

import budget.category.Category;

import java.util.Comparator;

public class CategorySort implements Comparator<Category> {
    @Override
    public int compare(Category a, Category b) {
        return Double.compare(
                b.getSumAmount().getAmount(),
                a.getSumAmount().getAmount()
        );
    }
}
