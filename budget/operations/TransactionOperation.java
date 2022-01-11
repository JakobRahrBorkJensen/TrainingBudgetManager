package budget.operations;

/**
 * Strategy container for the Operation strategy
 */
public class TransactionOperation {
    private Operation operation;

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void performOperation() {
        this.operation.doOperation();
    }
}
