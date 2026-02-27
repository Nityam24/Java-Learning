import java.lang.Math;

public class MathClass {

    static public String generateOTP() {
        int otp = (int) (Math.random() * 900000) + 100000; // 6-digit OTP: 100000 to 999999
        return String.valueOf(otp);
    }

    public static void main(String[] args) {
        System.out.println("J");
        // Math m = new Math(); // COMPILE ERROR — constructor is private!
        var res = Math.abs(-5); // ✅ Always use class name directly
        System.out.println(res);

        System.out.println(Math.PI); // 3.141592653589793
        System.out.println(Math.E); // 2.718281828459045

        double openPrice = 150.0;
        double closePrice = 142.5;
        double change = Math.abs(closePrice - openPrice); // 7.5 (regardless of direction)
        System.out.print(change);

        // Ensure volume stays between 0 and 100
        int requestedVolume = 150;
        int actualVolume = Math.min(100, Math.max(0, requestedVolume)); // 100
        // Math.max(0, 150) = 150, then Math.min(100, 150) = 100
        System.out.println(actualVolume);
        int negativeVolume = -5;
        int safeVolume = Math.min(100, Math.max(0, negativeVolume)); // 0
        System.out.println(safeVolume);

        double principal = 10000; // ₹10,000 invested
        double rate = 0.08; // 8% annual interest
        int years = 5;

        double futureValue = principal * Math.pow(1 + rate, years);
        // 10000 * (1.08)^5 = 14693.28
        System.out.printf("Future value: rupees %.2f%n", futureValue);

        double rawPrice = 199.3;

        // Supermarket always charges whole rupees (ceiling — customer pays more, never
        // less)
        long chargedPrice = (long) Math.ceil(rawPrice); // 200
        System.out.println(chargedPrice);
        // Taxi app shows fare to the floor (show lower number in ad)
        long displayFare = (long) Math.floor(rawPrice); // 199
        System.out.println(displayFare);

        // Bank rounds to nearest paisa (2 decimal places)
        double rounded = Math.round(rawPrice * 100.0) / 100.0; // 199.30
        System.out.println(rounded);

        String otp = MathClass.generateOTP();
        System.out.println(otp);

        // Rotate a point (x, y) by 'angle' degrees around the origin
        double x = 1.0, y = 0.0;
        double angle = 90; // degrees

        double newX = x * Math.cos(Math.toRadians(angle)) - y * Math.sin(Math.toRadians(angle));
        double newY = x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));

        System.out.printf("Rotated point: (%.1f, %.1f)%n", newX, newY);
        // Output: Rotated point: (0.0, 1.0)
        // → Point (1,0) rotated 90° becomes (0,1) ✓

    }
}
