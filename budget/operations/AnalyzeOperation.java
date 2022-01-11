package budget.operations;

import budget.spend_analysis.*;
import budget.transaction.TransactionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static budget.utils.ReceiveInputUtils.receiveMenuInput;

public class AnalyzeOperation implements Operation {
    private final Scanner scanner;
    private final TransactionService service;

    public AnalyzeOperation(Scanner scanner, TransactionService service) {
        this.scanner = scanner;
        this.service = service;
    }

    @Override
    public void doOperation() {
        SpendAnalysisContainer analyser = new SpendAnalysisContainer();
        Map<Integer, SpendAnalysis> actions = getActions(service, scanner);

        while (true) {
            int action = chooseTypeOfSort();

            // Input 4 = back
            if (action == 4) {
                return;
            }

            analyser.setAnalysis(actions.get(action));
            analyser.performAnalysis();
            System.out.println();
        }
    }

    /**
     * This method matches the chosen action with the corresponding SpendAnalysis.
     * If new analysis are to be supported, they should be added here.
     */
    private static Map<Integer, SpendAnalysis> getActions(TransactionService service, Scanner scanner) {
        Map<Integer, SpendAnalysis> actions = new HashMap<>();
        actions.put(1, new SortAllPurchasesSpendAnalysis(service));
        actions.put(2, new SortByTypeSpendAnalysis(service));
        actions.put(3, new SortCertainTypeSpendAnalysis(service, scanner));
        return actions;
    }

    private int chooseTypeOfSort() {
        showSortOptions();
        return receiveMenuInput(scanner);
    }

    private static void showSortOptions() {
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
    }
}
