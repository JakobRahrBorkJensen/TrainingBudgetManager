package budget.operations;

import budget.transaction.Transaction;
import budget.transaction.TransactionService;
import budget.transaction.TransactionType;
import budget.utils.Amount;

import java.util.Scanner;

/**
 * Handle menu option "Add Income".
 * Structured so that this class handles the UI (console), while business logic is placed in {@link TransactionService}
 */
public class AddIncomeOperation implements Operation {
    private final Scanner scanner;
    private final TransactionService service;

    public AddIncomeOperation(Scanner scanner, TransactionService service) {
        this.scanner = scanner;
        this.service = service;
    }

    @Override
    public void doOperation() {
        var incomeTransaction = receiveIncomeTransaction();
        service.addTransaction(incomeTransaction);
        System.out.println("Income was added!");
    }

    /**
     * Receives income transaction as raw inputs from command line.
     * Inputs will be converted into an income transaction
     */
    private Transaction receiveIncomeTransaction() {
        System.out.println("Enter income:");
        String rawInput = scanner.nextLine();

        return new Transaction.Builder()
                .transactionAmount(new Amount(Double.parseDouble(rawInput)))
                .type(TransactionType.INCOME)
                .build();
    }
}
