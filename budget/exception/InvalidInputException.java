package budget.exception;

import static java.lang.String.format;

/**
 * Exception for handling wrongful input
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(int providedInput) {
        super(format("[%s] was entered as input, which is invalid", providedInput));
    }
}
