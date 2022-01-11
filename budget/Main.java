package budget;

import budget.operations.*;
import budget.transaction.TransactionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static budget.utils.ReceiveInputUtils.receiveMenuInput;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String FILE_PATH = "purchases.txt";

    /**
     * Acts as the flow controller of this application.
     * Should not require changes if new actions are to be supported.
     */
    public static void main(String[] args) {
        TransactionService service = new TransactionService();
        TransactionOperation operator = new TransactionOperation();
        Map<Integer, Operation> actions = getActions(service);

        while (true) {
            int action = chooseAction();

            if (action == 0) break;

            operator.setOperation(actions.get(action));
            operator.performOperation();
            System.out.println();
        }
        System.out.println("Bye!");
    }

    /**
     * This method matches the chosen action with the corresponding operation.
     * If new actions are to be supported, they should be added here.
     */
    private static Map<Integer, Operation> getActions(TransactionService service) {
        Map<Integer, Operation> actions = new HashMap<>();
        actions.put(1, new AddIncomeOperation(SCANNER, service));
        actions.put(2, new AddPurchaseOperation(SCANNER, service));
        actions.put(3, new ShowListOfPurchasesOperation(SCANNER, service));
        actions.put(4, new BalanceOperation(service));
        actions.put(5, new SaveOperation(service, FILE_PATH));
        actions.put(6, new LoadOperation(service, FILE_PATH));
        actions.put(7, new AnalyzeOperation(SCANNER, service));
        return actions;
    }

    private static int chooseAction() {
        showMenu();
        return receiveMenuInput(SCANNER);
    }

    private static void showMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }
}
