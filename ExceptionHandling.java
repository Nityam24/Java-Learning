import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.ServiceUnavailableException;

class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void withdraw(double amount) {
        // Normal flow assumes balance is always sufficient
        // But what if it's not? That's an exceptional condition
        double newBalance = balance - amount;
        balance = newBalance;
    }
}

// Exception — you caused it, you can handle it
// int[] arr = new int[10];
// arr[15] = 5; // ArrayIndexOutOfBoundsException — you can catch and handle
// this

// Error — the JVM is broken, you cannot handle this meaningfully
// OutOfMemoryError — JVM has no heap memory left
// StackOverflowError — infinite recursion consumed all stack space

// 15.2 Exception Hierarchy

// Everything in Java's exception system inherits from `Throwable`.
// Understanding this tree is fundamental to writing good exception handling.
// Throwable
// ├── Error ← JVM-level problems, don't catch
// │ ├── OutOfMemoryError
// │ ├── StackOverflowError
// │ └── VirtualMachineError
// └── Exception ← Application-level problems, handle these
// ├── IOException ← Checked (must handle)
// ├── SQLException ← Checked (must handle)
// └── RuntimeException ← Unchecked (optional to handle)
// ├── NullPointerException
// ├── ArithmeticException
// ├── ArrayIndexOutOfBoundsException
// ├── ClassCastException
// ├── NumberFormatException
// ├── IllegalArgumentException
// ├── IllegalStateException
// ├── UnsupportedOperationException
// └── ConcurrentModificationException

class BankingExceptionDemo {

    public void demonstrateHierarchy() {
        // OutOfMemoryError — JVM ran out of heap. Cannot recover.
        // Happens if you load millions of transactions into memory at once.

        // StackOverflowError — infinite recursion
        // calculateInterest() calls itself forever without a base case.

        // IOException — reading transaction log file that doesn't exist
        // Must handle at compile time.

        // NullPointerException — user object is null, you call user.getName()
        // RuntimeException, unchecked.

        // ArithmeticException — dividing interest rate by zero
        // RuntimeException, unchecked.

        // NumberFormatException — user types "abc" as transfer amount
        // RuntimeException, unchecked.

        // ClassCastException — casting a SavingsAccount to LoanAccount incorrectly
        // RuntimeException, unchecked.

        // IllegalArgumentException — passing negative amount to withdraw()
        // RuntimeException, unchecked — good for argument validation.

        // IllegalStateException — trying to withdraw from a closed/frozen account
        // RuntimeException, unchecked — good for state validation.

        // UnsupportedOperationException — calling a method not supported on read-only
        // account
        // RuntimeException, unchecked.

        // ConcurrentModificationException — iterating transaction list while another
        // thread modifies it
        // RuntimeException, unchecked.
    }
}

// 1. Checked Exceptions
// ////////////////////////////////////////////////////////////////////
// The compiler forces you to handle these.
// If a method can throw a checked exception, you must either catch it with
// try-catch or declare it with throws.
// The reasoning is: these are foreseeable problems that good programs should
// explicitly address.

// IOException is checked — compiler forces you to handle it
// public void logTransaction(String transactionId) throws IOException {
// FileWriter writer = new FileWriter("transactions.log"); // can throw
// IOException
// writer.write("Transaction: " + transactionId);
// writer.close();
// }

// // If you don't declare throws, you MUST use try-catch
// public void logTransactionSafe(String transactionId) {
// try {
// FileWriter writer = new FileWriter("transactions.log");
// writer.write("Transaction: " + transactionId);
// writer.close();
// } catch (IOException e) {
// System.err.println("Failed to log transaction: " + e.getMessage());
// }
// }

// 2. Unchecked Exceptions (RuntimeException)
// The compiler does not force you to handle these.
// They represent programming errors — bugs in your code — that ideally
// shouldn't happen if you write correct code.
// You can catch them, but you're not required to.

// public class TransferService {

// public void transfer(String fromAccountId, String toAccountId, double amount)
// {
// // NullPointerException — unchecked. If fromAccountId is null,
// // this crashes at runtime. Fix the code, don't just catch it.
// BankAccount from = accountRepository.find(fromAccountId);

// // ArithmeticException — unchecked
// double fee = amount / 0; // programming error, fix the logic

// // NumberFormatException — unchecked
// // If user input comes as String, parse it safely
// double transferAmount = Double.parseDouble("abc123"); // crashes at runtime
// }
// }

// finally block:- ////////////////////////////////////////////////////////
// finally always executes, whether an exception was thrown or not, whether it
// was caught or not.
// Its purpose is cleanup — close database connections, release locks, close
// files.

// finally NOT executing ///////////////////////////////////////////////////
// public void processLargeTransfer(double amount) {
// try {
// if (amount > 10_000_000) {
// System.exit(1); // JVM shuts down immediately — finally does NOT run
// }
// // do transfer...
// } finally {
// System.out.println("This will NOT print if System.exit() was called");
// }
// }

// Multi-catch
// public class NotificationService {

// // BEFORE Java 7 — repetitive
// public void sendAlertOld(String userId) {
// try {
// sendEmail(userId);
// sendSms(userId);
// } catch (EmailException e) {
// logAlert(e);
// notifyAdminTeam(e);
// } catch (SmsException e) {
// logAlert(e); // exact same code
// notifyAdminTeam(e); // exact same code
// }
// }

// // AFTER Java 7 — clean multi-catch
// public void sendAlert(String userId) {
// try {
// sendEmail(userId);
// sendSms(userId);
// } catch (EmailException | SmsException e) {
// // Handle both the same way
// logAlert(e);
// notifyAdminTeam(e);
// }
// }

// // Multiple multi-catch blocks — fine
// public void processRequest(String userId, String amountStr) {
// try {
// double amount = Double.parseDouble(amountStr);
// User user = userRepository.findById(userId);
// processTransaction(user, amount);

// } catch (NumberFormatException | IllegalArgumentException e) {
// // Both are input validation issues — handle together
// throw new BadRequestException("Invalid input: " + e.getMessage());

// } catch (DatabaseException | NetworkException e) {
// // Both are infrastructure issues — handle together
// throw new ServiceUnavailableException("Service temporarily down", e);
// }
// }
// }

// Resource Declaration in try
// //////////////////////////////////////////////////////
// public void generateReport(String accountId) {
// // Resource declared in the try() parentheses
// // It will be closed automatically when the block exits
// try (Connection conn = dataSource.getConnection();
// PreparedStatement stmt = conn.prepareStatement(
// "SELECT * FROM transactions WHERE account_id = ?")) {

// stmt.setString(1, accountId);
// ResultSet rs = stmt.executeQuery();

// while (rs.next()) {
// System.out.println(rs.getString("description") + " - " +
// rs.getDouble("amount"));
// }
// // conn, stmt closed automatically here — even if exception occurs

// } catch (SQLException e) {
// System.err.println("Report generation failed: " + e.getMessage());
// }
// // No finally block needed for resource cleanup!
// }

// Suppressed Exceptions
// This is an advanced point that comes up in senior interviews.
// What happens if both the try block throws an exception and the close() method
// throws an exception?

// public class AuditLogger implements AutoCloseable {

// public void log(String message) throws Exception {
// throw new Exception("Logging failed — disk full");
// }

// @Override
// public void close() throws Exception {
// throw new Exception("Close failed — file handle stuck");
// }
// }

// public void auditTransfer() {
// try (AuditLogger logger = new AuditLogger()) {
// logger.log("Transfer of $500");
// // "Logging failed — disk full" is thrown here (PRIMARY exception)
// // When try-with-resources tries to close, "Close failed" is thrown too
// // The close() exception is SUPPRESSED — attached to the primary exception

// } catch (Exception e) {
// System.out.println("Primary: " + e.getMessage()); // Logging failed — disk
// full

// Throwable[] suppressed = e.getSuppressed();
// for (Throwable s : suppressed) {
// System.out.println("Suppressed: " + s.getMessage()); // Close failed — file
// handle stuck
// }
// }
// }

/*
 * In old-style finally blocks, the exception from close() would replace the
 * original exception — you'd lose the root cause.
 * try-with-resources preserves both by attaching the secondary exception as
 * suppressed.
 */

// Throw Keyword //////////////////////////////////////////////////////////////
// throw is how you manually signal that something has gone wrong in your code.
// public class BankAccount {
//     private double balance;
//     private boolean frozen;

    // public void withdraw(double amount) {
    // // Validate inputs — throw for bad arguments
    // if (amount <= 0) {
    // throw new IllegalArgumentException(
    // "Withdrawal amount must be positive, got: " + amount);
    // }

    // // Validate state — throw for invalid state
    // if (frozen) {
    // throw new IllegalStateException(
    // "Cannot withdraw from a frozen account");
    // }

    // // Validate business rule — throw custom exception
    // if (amount > balance) {
    // throw new InsufficientFundsException(
    // "Insufficient funds. Requested: " + amount + ", Available: " + balance,
    // balance,
    // amount - balance
    // );
    // }

    // this.balance -= amount;
    // }
    // }

    // // throw — an ACTION. You throw an exception object. Used inside method body.
    // throw new IllegalArgumentException("Bad input");

    // // throws — a DECLARATION. You announce that a method might throw.
    // // Used in the method signature. Required for checked exceptions.
    // public void readFile(String path) throws IOException {
    // // ...
    // }

    // throws with Method Overriding — Important Rule
    // -------------------------------------------
    // public class BasePaymentGateway {
    // public void processPayment(double amount) throws IOException {
    // // base implementation
    // }
    // }

    // public class StripePaymentGateway extends BasePaymentGateway {

    // // VALID — fewer exceptions than parent
    // @Override
    // public void processPayment(double amount) {
    // // no throws — perfectly fine
    // }

    // // VALID — same or narrower checked exception
    // // (FileNotFoundException is a subclass of IOException)
    // // @Override
    // // public void processPayment(double amount) throws FileNotFoundException { }

    // // INVALID — broader checked exception — COMPILE ERROR
    // // @Override
    // // public void processPayment(double amount) throws Exception { }

    // // VALID — unchecked exceptions can always be added
    // // @Override
    // // public void processPayment(double amount) throws RuntimeException { }
    // }

    // 15.11 Exception Handling Best
    // Practices////////////////////////////////////////////////

    // 1. Catch Specific Exceptions First
    // ----------------------------------------------------

    // Always catch the most specific exception before more general ones. More
    // specific exceptions are subclasses, so catching a parent class first would
    // make child class catches unreachable.
    // java// WRONG — FileNotFoundException is a subclass of IOException.
    // // The second catch is unreachable (compile error in this case).
    // try {
    // readStatementFile(accountId);
    // } catch (IOException e) {
    // handleIoError(e);
    // } catch (FileNotFoundException e) { // UNREACHABLE — compile error
    // handleMissingFile(e);
    // }

    // // CORRECT
    // try {
    // readStatementFile(accountId);
    // } catch (FileNotFoundException e) {
    // handleMissingFile(e); // specific first
    // } catch (IOException e) {
    // handleIoError(e); // general after
    // }

    // 2. Never Swallow Exceptions Silently
    // -----------------------------------------------------
    // An empty catch block is one of the worst things you can do. The exception is
    // gone, the problem remains, and you'll spend hours debugging with no clues.
    // java// TERRIBLE — exception swallowed. The program silently continues in a
    // broken state.
    // try {
    // conn = dataSource.getConnection();
    // executeTransfer(conn, amount);
    // } catch (SQLException e) {
    // // Empty catch block — you have no idea this failed
    // }

    // // MINIMUM — at least log it
    // try {
    // conn = dataSource.getConnection();
    // executeTransfer(conn, amount);
    // } catch (SQLException e) {
    // logger.error("Transfer failed for account {}: {}", accountId, e.getMessage(),
    // e);
    // throw new TransferFailedException("Transfer failed", e); // and rethrow
    // }

    // 3. Don't Use Exceptions for Flow Control
    // -----------------------------------------------
    // Exceptions are expensive (creating a stack trace is slow) and semantically
    // wrong for normal conditions.
    // java// WRONG — using exception to check if item exists
    // public boolean isAccountActive(String accountId) {
    // try {
    // BankAccount account = findAccount(accountId); // throws if not found
    // return account.isActive();
    // } catch (AccountNotFoundException e) {
    // return false; // Using exception as an if-statement — wrong!
    // }
    // }

    // // CORRECT — use normal control flow
    // public boolean isAccountActive(String accountId) {
    // Optional<BankAccount> account = accountRepository.findById(accountId);
    // return account.map(BankAccount::isActive).orElse(false);
    // }

    // 4. Log Before Rethrowing
    // ------------------------------------------------------
    // If you catch an exception and rethrow it (possibly wrapped), log it at the
    // point you have the most context. Don't let it bubble up silently and log it
    // at the top level without context.
    // javapublic void processMonthlyStatements(List<String> accountIds) {
    // for (String accountId : accountIds) {
    // try {
    // generateStatement(accountId);
    // } catch (StatementGenerationException e) {
    // // Log here — you know WHICH account failed
    // logger.error("Failed to generate statement for account {}: {}", accountId,
    // e.getMessage(), e);
    // // Decide: continue with next account, or throw?
    // // For batch processing, often better to continue and report at end
    // }
    // }
    // }

    // 5. Clean Up Resources in finally (or use try-with-resources)
    // -----------------------------------
    // Any resource you open, you are responsible for closing — even if an exception
    // occurs. Try-with-resources is the modern approach. When not possible, use
    // finally.
    // java// Preferred: try-with-resources
    // public List<Transaction> getTransactions(String accountId) throws
    // SQLException {
    // try (Connection conn = dataSource.getConnection();
    // PreparedStatement stmt = conn.prepareStatement(QUERY)) {
    // stmt.setString(1, accountId);
    // return mapResultSet(stmt.executeQuery());
    // }
    // // conn and stmt guaranteed to be closed
    // }

    // 6. Fail Fast Principle
    // -------------------------------------------------------------
    // Validate inputs at the earliest opportunity. Don't let invalid data travel
    // deep into your system before failing — the failure will be harder to diagnose
    // and may cause partial state changes.
    // javapublic class TransferService {

    // public TransferResult transfer(TransferRequest request) {
    // // Fail fast — validate at the entry point before doing anything
    // Objects.requireNonNull(request, "Transfer request cannot be null");
    // Objects.requireNonNull(request.getFromAccountId(), "Source account ID
    // required");
    // Objects.requireNonNull(request.getToAccountId(), "Destination account ID
    // required");

    // if (request.getAmount() <= 0) {
    // throw new IllegalArgumentException("Transfer amount must be positive: " +
    // request.getAmount());
    // }
    // if (request.getFromAccountId().equals(request.getToAccountId())) {
    // throw new IllegalArgumentException("Cannot transfer to same account");
    // }

    // // Only AFTER all validation passes do we touch the database
    // BankAccount source =
    // accountRepository.findOrThrow(request.getFromAccountId());
    // BankAccount destination =
    // accountRepository.findOrThrow(request.getToAccountId());

    // // ... rest of transfer logic
    // }
    // }

    // 7. Exception Message Quality
    // -------------------------------------------------------------
    // A good exception message should let a developer (or log aggregator) diagnose
    // the problem without needing to reproduce it.
    // java// BAD — no context, useless for debugging
    // throw new Exception("Error");
    // throw new RuntimeException("Failed");
    // throw new IllegalArgumentException("Invalid");

    // // GOOD — who, what, why, where
    // throw new InsufficientFundsException(
    // String.format("Withdrawal of %.2f denied for account %s (balance: %.2f,
    // shortfall: %.2f)",
    // amount, accountId, balance, amount - balance),
    // balance, amount, accountId
    // );

    // throw new AccountNotFoundException(
    // String.format("Account with ID '%s' not found in database (userId: %s)",
    // accountId, userId)
    // );

    // throw new IllegalArgumentException(
    // String.format("Transfer amount must be between %.2f and %.2f, got: %.2f",
    // MIN_TRANSFER, MAX_TRANSFER, amount)
    // );

    public class ExceptionHandling {
        public static void main(String[] args) {

        }
    }
