import java.lang.Math;


// // The contract — defines WHAT, not HOW
// interface Payable {
//     boolean processPayment(double amount);  // Every payment method MUST implement this
//     // Rule 2 — Methods are implicitly public abstract
//     void generateReceipt(String txnId);     // And this

//     // Java sees this as:
//     // public abstract boolean processPayment(double amount);
//     // You don't need to write public abstract — it's automatic
// }

// interface TaxConstants {
//     // Variables are implicitly public static final (constants)
//     double GST_RATE = 0.18;    // Java treats this as:
//     int    MAX_RETRY = 3;      // public static final double GST_RATE = 0.18;
//                                // public static final int MAX_RETRY = 3;
// }

// // UPI implements the contract its own way
// class UPIPayment implements Payable {
//     @Override
//     public boolean processPayment(double amount) {
//         System.out.println("Processing Rupee " + amount + " via UPI...");
//         return true;
//     }

//     @Override
//     public void generateReceipt(String txnId) {
//         System.out.println("UPI Receipt for TXN: " + txnId);
//     }
// }

// // Credit Card implements the same contract differently
// class CreditCardPayment implements Payable {
//     @Override
//     public boolean processPayment(double amount) {
//         System.out.println("Charging Rupee" + amount + " to credit card...");
//         return true;
//     }

//     @Override
//     public void generateReceipt(String txnId) {
//         System.out.println("Card Receipt for TXN: " + txnId);
//     }
// }

////////////////////////////////Interface Extending Interface////////////////////////////////////////////////////////

// interface Readable {
//     String read();
// }

// interface Writable {
//     void write(String data);
// }

// // ReadWritable inherits both contracts
// interface ReadWritable extends Readable, Writable {
//     void flush();  // Adds its own method too
// }

// // Implementing class must implement ALL methods from the entire hierarchy
// class FileStream implements ReadWritable {
//     @Override
//     public String read() { return "reading from file..."; }

//     @Override
//     public void write(String data) { System.out.println("Writing: " + data); }

//     @Override
//     public void flush() { System.out.println("Flushing buffer..."); }
// }

////////////////////////////////Default Methods (Java 8+) — Backward Compatibility//////////////////////////////////////////////
// interface Notification {
//     void send(String message);  // Abstract — must implement

//     // Default method — implementing classes get this for free
//     default void sendWithTimestamp(String message) {
//         String timestamped = "[" + java.time.LocalTime.now() + "] " + message;
//         send(timestamped);  // Delegates to the abstract method
//     }

//     default void sendBatch(String[] messages) {
//         for (String msg : messages) {
//             send(msg);
//         }
//     }
// }

// // EmailNotification only MUST implement send() — gets the defaults for free
// class EmailNotification implements Notification {
//     @Override
//     public void send(String message) {
//         System.out.println("EMAIL: " + message);
//     }
//     // sendWithTimestamp and sendBatch work automatically!
// }

// // SMSNotification overrides the default if needed
// class SMSNotification implements Notification {
//     @Override
//     public void send(String message) {
//         System.out.println("SMS: " + message);
//     }

//     @Override
//     public void sendWithTimestamp(String message) {
//         // SMS has character limits — override with shorter format
//         send("[" + java.time.LocalTime.now().getMinute() + "m] " + message);
//     }
// }

/////////////////////////Diamond Problem with Default Methods/////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

interface A {
    default void greet() {
        log("greeting from A");
        System.out.println("Hello from A");
    }
     static void isEmail() {
        // return input != null && input.contains("@") && input.contains(".");
        System.out.println("This is email checker from A");
    }

    // Private helper — not visible to implementing classes
    private void log(String message) {
        System.out.println("[" + java.time.Instant.now() + "] " + message);
    }
}

interface B {
    default void greet() {
        System.out.println("Hello from B");
    }
    static void isEmail() {
        // return input != null && input.contains("@") && input.contains(".");
        System.out.println("This is email checker from B");
    }
}

// COMPILE ERROR without override — ambiguous!
// public class C implements A, B { }

// Must resolve explicitly:
class C implements A, B {
    @Override
    public void greet() {
        A.super.greet(); // Explicitly choose A's version
        // or B.super.greet(); to choose B
        // or write your own entirely
        System.out.println("Hello from C (resolved)");
    }

    // @Override
    // public static void isEmail() {
    //     A.super.isEmail();          // compile error - can't do that as it is static method use as A.isEmail() directly
    // }
}

////////////////////////////////////// Functional Interfaces /////////////////////////////////////////////////////////
// An interface with exactly one abstract method. Also called a SAM (Single Abstract Method) interface.
//  Functional interfaces are the foundation of lambda expressions in Java.

@FunctionalInterface  // Optional but recommended — compiler enforces the one-method rule
interface Calculator {
    int compute(int a, int b);  // The one abstract method
    // Adding a second abstract method here would cause a compile error
}


public class Interface {
    public static void main(String[] args) {
        // CreditCardPayment ccp = new CreditCardPayment();
        // ccp.processPayment(123);

        // // Payable p = new Payable(); // COMPILE ERROR — interface has no
        // implementation

        // double tax = TaxConstants.GST_RATE; // Access via interface name
        // System.out.println("Here is Tax: " + tax);

        // Notification sms = new SMSNotification();
        // sms.send("This is sms type notification");
        // sms.sendWithTimestamp("this is sms");

        // Notification email = new EmailNotification();
        // email.send("This is Email type of Notification");
        // email.sendWithTimestamp("This is Email");

        A c = new C();
        c.greet(); // Hello from A
        A.isEmail();
        B.isEmail();

        Calculator cal = new Calculator() {
            @Override
            public int compute(int a, int b) { return a + b; }
        };
        
        // Lambda way (Java 8+) — much cleaner:
        Calculator add      = (a, b) -> a + b;
        Calculator subtract = (a, b) -> a - b;
        Calculator multiply = (a, b) -> a * b;

        System.out.println(add.compute(5, 3));       // 8
        System.out.println(subtract.compute(5, 3));  // 2
        System.out.println(multiply.compute(5, 3));  // 15

        ///////////////////////////////Built-in Functional Interfaces (java.util.function)//////////////////////////////////////
        /// 
        /// Predicate<T> — Test something, return boolean
        Predicate<String> isLongPassword = password -> password.length() >= 8;
        Predicate<Integer> isAdult       = age -> age >= 18;
        Predicate<String> isValidEmail   = email -> email.contains("@");

        System.out.println(isLongPassword.test("abc"));       // false
        System.out.println(isAdult.test(20));                 // true
        System.out.println(isValidEmail.test("user@test.com")); // true

        /////////Function<T, R> — Transform input T into output R
        Function<String, Integer> toLength    = str -> str.length();
        Function<String, String>  toUpperCase = str -> str.toUpperCase();
        Function<Integer, String> toLabel     = score -> score >= 50 ? "PASS" : "FAIL";

        System.out.println(toLength.apply("Hello"));     // 5
        System.out.println(toUpperCase.apply("hello"));  // "HELLO"
        System.out.println(toLabel.apply(75));           // "PASS"

        // Chaining with andThen:
        Function<String, String> trimAndUpper = ((Function<String, String>) String::trim)
            .andThen(String::toUpperCase);
        System.out.println(trimAndUpper.apply("  hello  "));  // "HELLO"

        // Real-world: mapping objects
        List<String> names = Arrays.asList("alice", "bob", "charlie");
        List<Integer> lengths = names.stream()
            .map(toLength)
            .collect(Collectors.toList());
        System.out.println(lengths);  // [5, 3, 7]


        ///////Consumer<T> — Accept input, return nothing (side effects)
        // Signature: void accept(T t)
        // Use: printing, logging, saving, sending notifications

        Consumer<String> printLog     = msg -> System.out.println("[LOG] " + msg);
        Consumer<String> sendEmail    = email -> System.out.println("Sending email to: " + email);
        Consumer<Integer> saveToDb    = id -> System.out.println("Saving record ID: " + id);

        printLog.accept("User logged in");          // [LOG] User logged in
        sendEmail.accept("user@example.com");       // Sending email to: user@example.com

        // Chaining with andThen:
        Consumer<String> logAndEmail = printLog.andThen(sendEmail);
        logAndEmail.accept("admin@example.com");
        // [LOG] admin@example.com
        // Sending email to: admin@example.com

        // Real-world: forEach with Consumer
        List<String> users = Arrays.asList("Alice", "Bob", "Charlie");
        users.forEach(user -> System.out.println("Welcome, " + user + "!"));

        //// Supplier<T> — Supply a value, takes no input java
        // Signature: T get()
        // Use: lazy initialization, factories, providing defaults, generating values

        Supplier<String>  getGreeting  = () -> "Hello, World!";
        Supplier<Double>  getRandomNum = () -> Math.random() * 100;
        Supplier<List<String>> newList = () -> new ArrayList<>();

        System.out.println(getGreeting.get());   // "Hello, World!"
        System.out.println(getRandomNum.get());  // e.g., 73.45
        System.out.println(newList.get());       // []

        //// BiFunction<T, U, R> — Two inputs, one output
        // Signature: R apply(T t, U u)
        // Use: combining two things into one result

        BiFunction<String, Integer, String> repeat = (str, times) -> str.repeat(times);
        BiFunction<Integer, Integer, Integer> power = (base, exp) -> (int) Math.pow(base, exp);
        BiFunction<String, String, String> fullName = (first, last) -> first + " " + last;

        System.out.println(repeat.apply("Ha", 3));         // "HaHaHa"
        System.out.println(power.apply(2, 8));              // 256
        System.out.println(fullName.apply("John", "Doe")); // "John Doe"

        //// BiPredicate<T, U> — Two inputs, returns boolean
        // Signature: boolean test(T t, U u)

        BiPredicate<String, Integer> isLongerThan = (str, len) -> str.length() > len;
        BiPredicate<Integer, Integer> isInRange   = (val, max) -> val >= 0 && val <= max;

        System.out.println(isLongerThan.test("Hello", 3));   // true
        System.out.println(isInRange.test(50, 100));          // true
        System.out.println(isInRange.test(150, 100));         // false

        //// Custom Functional Interface — Real-World Example
        @FunctionalInterface
        interface TriFunction<A, B, C, R> {
            R apply(A a, B b, C c);
        }

        // Use: calculate EMI (principal, rate, months)
        TriFunction<Double, Double, Integer, Double> calculateEMI =
            (principal, annualRate, months) -> {
                double monthlyRate = annualRate / 12 / 100;
                return (principal * monthlyRate * Math.pow(1 + monthlyRate, months))
                    / (Math.pow(1 + monthlyRate, months) - 1);
            };

        double emi = calculateEMI.apply(500000.0, 8.5, 60);  // ₹5L loan, 8.5%, 5 years
        System.out.printf("Monthly EMI: ₹%.2f%n", emi);  // ₹10,224.67

        /////////////////////////// Marker Interfaces //////////////////////////////////////////////////////
        // A marker interface is an interface with NO methods and NO constants. 
        // It purely "tags" a class to signal something to the JVM or a framework.

    //     public interface Serializable { }   // No methods — just a tag
    //     public interface Cloneable { }      // No methods — just a tag
    //     public interface RandomAccess { }   // No methods — just a tag
            
        
    }
}
