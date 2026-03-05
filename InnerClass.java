
import java.util.List;

//  Regular Inner Class
//  When to use: 
//  When the inner class is tightly coupled to the outer class, 
//  needs access to the outer class's private state, and has no meaning on its own.
class BankAccount {

    private String accountNumber;
    private double balance;
    private String ownerName;

    public BankAccount(String accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    // Regular inner class — can access ALL outer class members including private
    public class TransactionRecord {

        private String type;
        private double amount;
        private java.time.LocalDateTime timestamp;

        public TransactionRecord(String type, double amount) {
            this.type = type;
            this.amount = amount;
            this.timestamp = java.time.LocalDateTime.now();
        }

        public void printReceipt() {
            // Directly accesses outer class's private members — no getter needed!
            System.out.println("=== RECEIPT ===");
            System.out.println("Account: " + accountNumber);   // outer private field
            System.out.println("Owner:   " + ownerName);       // outer private field
            System.out.println("Type:    " + type);
            System.out.println("Amount:  ₹" + amount);
            System.out.println("Balance: ₹" + balance);        // outer private field
            System.out.println("Time:    " + timestamp);
        }
    }

    public void deposit(double amount) {
        balance += amount;
        TransactionRecord record = new TransactionRecord("CREDIT", amount);
        record.printReceipt();
    }
}

// 2. Static Nested Inner class
// When to use: 
// Builder pattern, helper classes that are logically grouped with the outer class but don't 
// need access to instance state. 
// HashMap.Entry in Java's standard library is a famous example 
class HttpRequest {
    private String method;
    private String url;
    private java.util.Map<String, String> headers;
    private String body;

    private HttpRequest() { }  // private — use the Builder

    // Static nested Builder class — no need for HttpRequest instance to create it
    public static class Builder {
        private String method = "GET";
        private String url;
        private java.util.Map<String, String> headers = new java.util.HashMap<>();
        private String body;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            HttpRequest request = new HttpRequest();  // CAN access outer's private constructor!
            request.method  = this.method;
            request.url     = this.url;
            request.headers = this.headers;
            request.body    = this.body;
            return request;
        }
    }

    @Override
    public String toString() {
        return method + " " + url + " | headers: " + headers;
    }
}

// 3. Local Inner Class
// Defined inside a method body. Its scope is limited to that method — it cannot be used outside.
// When to use: 
// When a helper class is needed only once, inside a specific method, 
// and creating a top-level or static nested class would be overkill. 
// Rarely used in modern code — anonymous classes or lambdas usually serve the same purpose more concisely.

class ReportGenerator {

    public void generateSalesReport(List<?> orders) {

        // Local inner class — only exists inside generateSalesReport()
        class OrderSummary {
            String category;
            double total;
            int count;

            OrderSummary(String category) {
                this.category = category;
                this.total = 0;
                this.count = 0;
            }

            void add(double amount) {
                total += amount;
                count++;
            }

            void print() {
                System.out.printf("%-20s Count: %3d  Total: ₹%.2f  Avg: ₹%.2f%n",
                    category, count, total, count > 0 ? total / count : 0);
            }
        }

        // Use the local class within the method:
        OrderSummary electronics = new OrderSummary("Electronics");
        OrderSummary clothing    = new OrderSummary("Clothing");
        OrderSummary food        = new OrderSummary("Food");

        for (var order : orders) {
            // switch (order.getCategory()) {
            //     case "Electronics" -> electronics.add(order.getAmount());
            //     case "Clothing"    -> clothing.add(order.getAmount());
            //     case "Food"        -> food.add(order.getAmount());
            // }
        }

        System.out.println("=== SALES REPORT ===");
        electronics.print();
        clothing.print();
        food.print();
    }
    // OrderSummary is completely unknown/inaccessible outside this method
}


// 4. Anonymous Inner Class
// Extending an abstract class:
abstract class DataProcessor {
    abstract void process(String data);
    void log(String msg) { System.out.println("[LOG] " + msg); }
}

public class InnerClass {

    public static void main(String[] args) {
        // Inner class
        // Creating an inner class object REQUIRES an outer class instance:
        BankAccount account = new BankAccount("ACC001", "Alice", 10000.0);
        account.deposit(5000.0);

        // Creating inner class object from outside:
        BankAccount.TransactionRecord record = account.new TransactionRecord("MANUAL", 0);
        // Note the unusual syntax: outerInstance.new InnerClass()

        // 2. Nested Inner Class      
        // Creating static nested class WITHOUT any outer instance:
        HttpRequest request = new HttpRequest.Builder()   // no outer instance needed
            .url("https://api.example.com/users")
            .method("POST")
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer token123")
            .body("{\"name\": \"Alice\"}")
            .build();

        System.out.println(request);

        // 4. Anonymous Inner Class
        // Implementing an interface anonymously using lambda expression:
        Runnable task = () -> System.out.println("Running in a background thread!");
        // new Thread(task).start();

        // Extending an abstract class anonymously:
        DataProcessor csvProcessor = new DataProcessor() {
            @Override
            void process(String data) {
                log("Processing CSV...");  // CAN call the abstract class's concrete method
                String[] fields = data.split(",");
                System.out.println("Found " + fields.length + " fields.");
            }
        };

        csvProcessor.process("Alice,30,Engineer");
        // [LOG] Processing CSV...
        // Found 3 fields.
    }
}
