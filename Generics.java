// Defining a generic class — T is the type parameter

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

class Repository<T> {
    private List<T> items = new ArrayList<>();
    private String repositoryName;

    public Repository(String name) {
        this.repositoryName = name;
    }

    public void save(T item) {
        items.add(item);
    }

    public T findById(int index) {
        return items.get(index);
    }

    public List<T> findAll() {
        return Collections.unmodifiableList(items);
    }

    public int count() {
        return items.size();
    }
}

// Generic Class with Bounded Type
// T must be a Number or a subclass of Number (Integer, Double, BigDecimal,
// etc.)
class FinancialCalculator<T extends Number> {
    private List<T> values = new ArrayList<>();

    public void addValue(T value) {
        values.add(value);
    }

    public double sum() {
        return values.stream()
                .mapToDouble(Number::doubleValue) // can call doubleValue() because T extends Number
                .sum();
    }

    public double average() {
        return values.stream()
                .mapToDouble(Number::doubleValue)
                .average()
                .orElse(0.0);
    }
}

class TransactionUtils {

    // Generic method — <T> declared before return type
    // This method can work with any type T
    public <T> List<T> filterNonNull(List<T> items) {
        return items.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // Generic method that returns a different type from the input
    public <T, R> List<R> transform(List<T> items, Function<T, R> transformer) {
        return items.stream()
                .map(transformer)
                .collect(Collectors.toList());
    }

    // Generic method with bound
    public <T extends Comparable<T>> T findMax(List<T> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        T max = items.get(0);
        for (T item : items) {
            if (item.compareTo(max) > 0) {
                max = item;
            }
        }
        return max;
    }
}

public class Generics {
    public static void main(String[] args) {
        Repository<BankAccount> accountRepo = new Repository<>("AccountRepository");
        Repository<Transaction> txnRepo = new Repository<>("TransactionRepository");

        // Diamond operator <> — type inferred from the left side (Java 7+)
        Repository<Customer> customerRepo = new Repository<>("CustomerRepository"); // long form
        Repository<Customer> customerRepo1 = new Repository<>("CustomerRepository"); // same, diamond inferred

        FinancialCalculator<Double> doubleCalc = new FinancialCalculator<>(); // fine
        FinancialCalculator<Integer> intCalc = new FinancialCalculator<>(); // fine
        FinancialCalculator<BigDecimal> decCalc = new FinancialCalculator<>(); // fine
        // FinancialCalculator<String> strCalc = new FinancialCalculator<>(); // COMPILE
        // ERROR
        // String is not a Number — the bound is enforced at compile time

        // Calling — type is usually inferred by the compiler
        TransactionUtils utils = new TransactionUtils();

        List<String> ids = Arrays.asList("ACC001", null, "ACC002", null, "ACC003");
        List<String> validIds = utils.filterNonNull(ids); // compiler infers T = String

        // List<BankAccount> accounts = getAccounts();
        // List<String> names = utils.transform(accounts, BankAccount::getOwnerName);
        // compiler infers T = BankAccount, R = String

        // Explicit type specification (rare, but sometimes needed)
        List<Double> amounts = Arrays.asList(100.0, 500.0, 50.0, 750.0);
        Double maxAmount = utils.<Double>findMax(amounts); // explicit, T = Double
        Double maxAmount2 = utils.findMax(amounts); // inferred — same result, cleaner
    }
}
