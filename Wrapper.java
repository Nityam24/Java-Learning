import java.util.ArrayList;
import java.util.List;

public class Wrapper {

    public int i; // 0
    public Integer I; // null
    public static int ps; // 0

    // Before Java 5 (manual boxing):
    Integer obj1 = Integer.valueOf(42); // Had to do this manually

    // After Java 5 (autoboxing):
    Integer obj = 42; // Java automatically calls Integer.valueOf(42) behind the scenes

    Integer a = 5; // Assignment
    int x = 5;
    Integer b = x + 10; // Expression result autoboxed
    // someMethod(x); // Passing primitive to a method expecting Integer

    public double calculateAverage(List<Integer> scores) {
        int total = 0;
        for (Integer score : scores) {
            total += score; // 💥 NPE if any score is null (absent student)
        }
        return total / scores.size();
    }

    // parseInt / parseLong — String to number
    String ageInput = "25";
    int age = Integer.parseInt(ageInput); // 25

    String bigNumber = "9876543210";
    long big = Long.parseLong(bigNumber); // 9876543210L

    // parseDouble / parseFloat — String to decimal
    String priceStr = "199.99";
    double price = Double.parseDouble(priceStr); // 199.99

    // parseBoolean
    String flag = "true";
    boolean isActive = Boolean.parseBoolean(flag); // true
    // Note: only "true" (case-insensitive) returns true; everything else returns
    // false

    // parseChar? There is none. Use:
    char ch = "A".charAt(0); // Standard way to get char from String

    public static void main(String[] args) {
        // List<int> numbers = new ArrayList<>(); // COMPILE ERROR

        // You CAN do this:
        List<Integer> numbers = new ArrayList<>(); // Works! Integer is an object.
        Wrapper w = new Wrapper();

        System.out.println(w.i);
        System.out.println(w.I);
        System.out.println(ps);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Unboxing – Wrapper → Primitive (Automatic)
        // Java automatically extracts the primitive value from a wrapper when needed.
        Integer wrappedValue = Integer.valueOf(100);

        // Before Java 5 (manual unboxing):
        int raw1 = wrappedValue.intValue();

        // After Java 5 (auto-unboxing):
        int raw2 = wrappedValue; // Java calls wrappedValue.intValue() automatically

        // Real-world example — Doing math with List values:
        List<Integer> prices = new ArrayList<>();
        prices.add(250);
        prices.add(300);

        int total = 0;
        for (Integer price : prices) {
            total += price; // price (Integer) auto-unboxed to int for arithmetic
        }
        System.out.println("Total: " + total); // Total: 550
        /////////////////////////////////////////////////////////////////////////////////////////////////////

        Boolean.parseBoolean("yes"); // false! Not "true", so false.
        Boolean.parseBoolean("TRUE"); // true — case insensitive

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        Integer a = Integer.valueOf(42); // int → Integer (uses cache if -128 to 127)
        Integer b = Integer.valueOf("42"); // String → Integer
        Double d = Double.valueOf("3.14"); // String → Double
        Boolean bool = Boolean.valueOf("true"); // String → Boolean

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        int x = Integer.parseInt("42"); // Returns primitive int
        Integer y = Integer.valueOf("42"); // Returns Integer object

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        // compareTo — instance method, compares two Integer objects
        Integer a1 = 10;
        Integer b1 = 20;
        int result = a1.compareTo(b1); // Negative if a < b, 0 if equal, positive if a > b
        System.out.println(result); // -1 (or some negative number)

        // compare — static method (preferred, avoids NPE risk with auto-unboxing)
        int r = Integer.compare(10, 20); // -1
        int r2 = Integer.compare(20, 20); // 0
        int r3 = Integer.compare(30, 20); // 1

        // equals
        Integer x1 = 100;
        Integer y1 = 100;
        System.out.println(x1.equals(y1)); // true — compares value

        // Never use == for comparing Integer objects (see 8.3 — the cache trap!)

        /////////////////////////////////////////////////////////////////////////////////////////
        System.out.println(Integer.MAX_VALUE); // 2147483647 (about 2.1 billion)
        System.out.println(Integer.MIN_VALUE); // -2147483648

        System.out.println(Long.MAX_VALUE); // 9223372036854775807 (about 9.2 quintillion)
        System.out.println(Double.MAX_VALUE); // 1.7976931348623157E308

        System.out.println(Byte.MAX_VALUE); // 127
        System.out.println(Short.MAX_VALUE); // 32767

        /////////////////////////////////////////////////////////////////////////////////////////
        // Within cache range (-128 to 127):
        Integer a2 = 127;
        Integer b2 = 127;
        System.out.println(a2 == b2); // ✅ true — SAME cached object
        System.out.println(a2.equals(b2)); // ✅ true

        // Outside cache range:
        Integer x2 = 128;
        Integer y2 = 128;
        System.out.println(x2 == y2); // ❌ false — DIFFERENT objects created each time
        System.out.println(x2.equals(y2)); // ✅ true — equals() compares VALUES

        // Boolean — always returns cached TRUE/FALSE
        Boolean b3 = true;
        Boolean b4 = true;
        System.out.println(b3 == b4); // true — same cached Boolean.TRUE object

        // Character within range:
        Character c1 = 'A'; // 'A' is 65, within 0-127
        Character c2 = 'A';
        System.out.println(c1 == c2); // true — cached

        // Float and Double never be cached
    }

}
