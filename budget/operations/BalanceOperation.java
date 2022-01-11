package budget.operations;

import budget.transaction.TransactionService;

/**
 * Handle menu option "Balance".
 * Structured so that this class handles the UI (console), while business logic is placed in {@link TransactionService}
 */
public class BalanceOperation implements Operation {
    private final TransactionService service;

    public BalanceOperation(TransactionService service) {
        this.service = service;
    }

    @Override
    public void doOperation() {
        System.out.println("Balance: " + service.getBalance());
    }
}
