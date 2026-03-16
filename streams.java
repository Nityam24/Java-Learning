import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class streams {

    public void processOrder() {
        String prefix = "ORDER"; // effectively final — never reassigned
        int tax = 18; // effectively final

        // WORKS — prefix and tax are effectively final
        Function<String, String> label = id -> prefix + "-" + id;
        Function<Double, Double> withTax = price -> price * (1 + tax / 100.0);

        System.out.println(label.apply("001")); // ORDER-001
        System.out.println(withTax.apply(100.0)); // 118.0

        // COMPILE ERROR — counter is reassigned
        int counter = 0;
        Runnable bad = () -> System.out.println(counter); // ERROR!
        // counter++; // This makes counter NOT effectively final
    }

    /*
     * Stream Pipeline — The Mental Model
     * Source
     * (Collection/Array)
     * →
     * Intermediate Op
     * (filter/map) LAZY
     * →
     * Intermediate Op
     * (sorted/distinct)
     * →
     * Terminal Op
     * (collect/forEach) EAGER
     */

    public static void main(String[] args) {
        // ─── FORM 1: No parameters ───────────────────────────────
        Runnable r = () -> System.out.println("Order processing started");
        r.run(); // Output: Order processing started

        // ─── FORM 2: One param — parentheses OPTIONAL ────────────
        Consumer<String> greet = name -> System.out.println("Hello, " + name);
        greet.accept("Alice"); // Hello, Alice

        // same with parens — also valid
        Consumer<String> greet2 = (name) -> System.out.println("Hello, " + name);
        greet2.accept("Booby"); // Hello, Bobby

        // ─── FORM 3: Multiple params ─────────────────────────────
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println(add.apply(10, 20)); // 30

        // ─── FORM 4: Expression body (no braces, implicit return) ─
        Function<String, Integer> length = str -> str.length();
        System.out.println(length.apply("Invoice")); // 7

        // ─── FORM 5: Block body (braces, explicit return) ─────────
        Function<Double, Double> applyTax = (price) -> {
            double tax = price * 0.18; // 18% GST
            return price + tax;
        };
        System.out.println(applyTax.apply(100.0)); // 118.0

        // ─── FORM 6: Return in lambda ─────────────────────────────
        Predicate<Integer> isEligible = (age) -> {
            if (age < 18)
                return false;
            return true;
        };
        System.out.println(isEligible.test(21)); // true

        // =============================================== Creating Streams
        // =========================================================
        // 1. From Collection
        // List<Order> orders = getOrders();
        // Stream<Order> stream1 = orders.stream();

        // // 2. From Array
        // Order[] orderArray = getOrderArray();
        // Stream<Order> stream2 = Arrays.stream(orderArray);

        // // 3. Stream.of() — from literal values
        // Stream<String> stream3 = Stream.of("a", "b", "c");

        // // 4. Stream.empty() — empty stream (useful as default return)
        // Stream<Order> empty = Stream.empty();

        // // 5. Stream.iterate() — infinite stream with seed and function
        // Stream<Integer> nums = Stream.iterate(1, n -> n + 1); // 1, 2, 3, 4 ...
        // nums.limit(5).forEach(System.out::println); // 1 2 3 4 5

        // // 6. Stream.generate() — infinite stream from Supplier
        // Stream<Double> randoms = Stream.generate(Math::random);
        // randoms.limit(3).forEach(System.out::println);

        // // 7. Primitive Streams — avoid boxing overhead
        // IntStream intStream = IntStream.range(1, 6); // 1,2,3,4,5
        // IntStream intRange = IntStream.rangeClosed(1, 5); // 1,2,3,4,5
        // LongStream longs = LongStream.of(100L, 200L);
        // DoubleStream doubles = DoubleStream.of(1.1, 2.2);

        // =================================================== Intermediate Operations
        // (Lazy) =============================================
        // Intermediate operations are lazy. They build up a pipeline but do NOT execute
        // until a terminal operation is called. Each operation returns a new Stream.
        // Multiple intermediates can be chained.
        /*
         * 
         * filter() — keep elements that match a condition
         * map() — transform each element
         * mapToInt / mapToDouble / mapToLong — map to primitive streams
         * mapToObj() — convert primitive stream back to object stream
         * flatMap() — flatten nested collections (most important!)
         * distinct() — remove duplicates (uses equals/hashCode)
         * sorted() — sort elements
         * limit() and skip() — pagination
         * peek() — debugging (look but don't change)
         */

        // ===================================================== Terminal Operations (Eager) ====================================================
        /*
        Terminal operations trigger the pipeline to execute. They are eager — the moment you call a terminal op, all intermediate ops run.
        After a terminal op, the stream is consumed and cannot be reused.
        */
       /*
        forEach / forEachOrdered
        orders.stream().forEach(o -> sendEmail(o));

        collect() — most powerful terminal op
        List<Order> list = orders.stream().filter(...).collect(Collectors.toList());

        count(), min(), max()
        long total = orders.stream().filter(o -> o.getAmount() > 100).count();
        // max — returns Optional
        Optional<Order> biggestOrder = orders.stream().max(Comparator.comparing(Order::getAmount));
        biggestOrder.ifPresent(o -> System.out.println("Biggest: " + o.getAmount()));

        // min
        Optional<Order> smallest = orders.stream().min(Comparator.comparing(Order::getAmount));

         sum(), average(), summaryStatistics() — primitive streams only
         reduce() — fold elements into a single value
         findFirst(), findAny()
         anyMatch(), allMatch(), noneMatch() — short-circuit!
         toArray()
       */


        //  =============================================  Collectors ========================================================
        /*
            Collectors are strategies for accumulating stream elements into a result container. 
            They are used as arguments to collect(). The Collectors class provides a rich set of factory methods.
        */

        /*
            toList(), toSet(), toUnmodifiableList()
            List<Order> list = orders.stream().collect(Collectors.toList());
            Set<String> ids  = orders.stream().map(Order::getId).collect(Collectors.toSet());

            // Java 10+ — immutable list
            List<Order> immutable = orders.stream().collect(Collectors.toUnmodifiableList());

            toMap() — convert to a Map
            Map<String, Order> orderMap = orders.stream()
            .collect(Collectors.toMap(
                Order::getId,       // key extractor
                order -> order      // value extractor (or Function.identity())
            ));

             joining() — concatenate strings
             groupingBy() — most powerful collector!
             partitioningBy() — split into true/false groups
             counting(), summingInt(), averagingInt(), summarizingInt()
             toCollection() — collect to specific collection type
             mapping() — transform then collect (inside groupingBy)
             collectingAndThen() — collect then transform the result
        */
    }
}
