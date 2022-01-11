package budget.operations;

import budget.transaction.Transaction;
import budget.transaction.TransactionService;
import budget.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystemException;
import java.util.List;

/**
 * Handle menu option "Save".
 * Structured so that this class handles the UI (console), while business logic is placed in {@link TransactionService}
 */
public class SaveOperation implements Operation {
    private final TransactionService service;
    private final String filePath;


    public SaveOperation(TransactionService service, String filePath) {
        this.service = service;
        this.filePath = filePath;
    }

    @Override
    public void doOperation() {
        var transactionFile = createStorageFile();
        printTransactionsToFile(transactionFile, service.getTransactions());
        System.out.println("Purchases were saved!");
    }

    private void printTransactionsToFile(File transactionFile, List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(transactionFile)) {
            transactions.stream()
                    .map(StorageUtils::serializeToStorage)
                    .forEach(writer::println);
        } catch (IOException e) {
            System.out.printf("An exception occurred when printing to file: [%s]%n", e.getMessage());
        }
    }

    private File createStorageFile() {
        File transactionsFile = new File(filePath);
        try {
            var wasCreated = transactionsFile.createNewFile();
            if (!wasCreated) {
                throw new FileSystemException("Transaction file was not created!");
            }
        } catch (IOException ignored) {
        }
        return transactionsFile;
    }
}
