package budget.utils;

import budget.category.PurchaseCategory;
import budget.exception.InvalidInputException;
import budget.operations.ShowListOfPurchasesOperation;

import java.util.Arrays;
import java.util.Scanner;

/**
 * This class contains methods for receiving and validating inputs
 */
public class ReceiveInputUtils {

    public static Integer receiveMenuInput(Scanner scanner) {
        Integer menuInput = null;
        while (true) {
            String input = scanner.nextLine();

            //validate input
            menuInput = tryParseInt(input);
            System.out.println();
            if (menuInput != null) break;
        }
        return menuInput;
    }

    private static Integer tryParseInt(String input) {
        Integer parsedInt = null;
        try {
            parsedInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Input not possible (not an integer)");
        }
        return parsedInt;
    }

    /**
     * Method translates chosen action into corresponding purchase category.
     *
     * Be aware, the implementation is using a weak mapping by the Enum ordinal:
     * Benefit: If new enum values are added, this method do not need to be updated.
     * Drawback: Order in menu has to strictly match the ordinal.
     * Therefore, this method must be kept in sync with {@link ShowListOfPurchasesOperation}
     *
     * @param action option picked by user
     * @return Purchase category matching the action selected,
     * or throws {@link InvalidInputException} if action is not valid
     */
    public static PurchaseCategory convertToPurchaseCategory(int action) {
        return Arrays.stream(PurchaseCategory.values())
                .filter(val -> val.ordinal() == action - 1)
                .findFirst()
                .orElseThrow(() -> new InvalidInputException(action));
    }
}
