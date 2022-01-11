package budget.operations;

import budget.transaction.Transaction;
import budget.transaction.TransactionService;
import budget.utils.StorageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Handle menu option "Load".
 * Structured so that this class handles the UI (console), while business logic is placed in {@link TransactionService}
 */
public class LoadOperation implements Operation {
    private final TransactionService service;
    private final String filePath;

    public LoadOperation(TransactionService service, String filePath) {
        this.service = service;
        this.filePath = filePath;
    }

    @Override
    public void doOperation() {
        var storedTransactions = getTransactionsFromStorage();
        service.overwriteTransactions(storedTransactions);
        System.out.println("Purchases were loaded!");
    }

    private List<Transaction> getTransactionsFromStorage() {
        File transactionsFile = new File(filePath);
        List<String> fileLines = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(transactionsFile)) {
            while (fileScanner.hasNextLine()) {
                fileLines.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found upon load");
        }

        return mapToTransactions(fileLines);
    }

    private List<Transaction> mapToTransactions(List<String> fileLines) {
        return fileLines.stream()
                .map(StorageUtils::deserializeFromStorage)
                .collect(Collectors.toList());
    }
}
