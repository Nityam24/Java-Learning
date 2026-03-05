
import java.util.ArrayList;
import java.util.List;

class Printer {

    // Three methods, same name, different signatures
    void print(String message) {
        System.out.println("STRING: " + message);
    }

    void print(int number) {
        System.out.println("INT: " + number);
    }

    void print(String message, int copies) {
        for (int i = 0; i < copies; i++) {
            System.out.println("COPY " + (i + 1) + ": " + message);
        }
    }

    void test(long x) {
        System.out.println("long");
    }

    void test(Integer x) {
        System.out.println("Integer");
    }

    void test(int... x) {
        System.out.println("varargs");
    }
}

abstract class NotificationChannel {

    String recipientId;

    public NotificationChannel(String recipientId) {
        this.recipientId = recipientId;
    }

    abstract void send(String message);  // each channel sends differently

    void sendUrgent(String message) {
        System.out.println("[URGENT] Sending to: " + recipientId);
        send("🚨 URGENT: " + message);  // calls overridden version at runtime
    }

    // This method works for ANY NotificationChannel subclass:
    static final public void broadcastAlert(NotificationChannel channel, String message) {
        channel.send(message);  // doesn't know or care if it's Email, SMS, or WhatsApp
    }
}

class EmailChannel extends NotificationChannel {

    public EmailChannel(String email) {
        super(email);
    }

    @Override
    void send(String message) {
        System.out.println("📧 EMAIL to " + recipientId + ": " + message);
    }
}

class SMSChannel extends NotificationChannel {

    public SMSChannel(String phone) {
        super(phone);
    }

    @Override
    void send(String message) {
        System.out.println("📱 SMS to " + recipientId + ": " + message);
    }
}

class WhatsAppChannel extends NotificationChannel {

    public WhatsAppChannel(String phone) {
        super(phone);
    }

    @Override
    void send(String message) {
        System.out.println("💬 WHATSAPP to " + recipientId + ": " + message);
    }
}

public class Polymorphism {

    public static void main(String[] args) {
        Printer pr = new Printer();
        pr.print("Hey this is one");
        pr.print(89);
        pr.print("this is third", 3);

        // Exact match for int would be: void test(int x) — if present, picked first
        pr.test(5);
        // No exact int match →
        // Step 2: widen int to long → test(long) picked → prints "long"
        // If test(long) didn't exist → Step 3: autobox to Integer → prints "Integer"
        // If test(Integer) didn't exist → Step 4: varargs → prints "varargs"

        // operator overloading is not possible in java 
        // + operator — works differently based on context:
        int a = 5 + 3;           // arithmetic addition → 8
        String s = "Hello" + " World";  // string concatenation → "Hello World"
        String mixed = "Score: " + 95;  // int auto-converted to String → "Score: 95"
        System.out.println("this is its output:" + a + " " + s + " " + mixed);

        ////////////Runtime Polymorphism //////////////////////////////
        // Runtime polymorphism in action — one loop, three different behaviors:
        List<NotificationChannel> channels = new ArrayList<>();
        channels.add(new EmailChannel("alice@example.com"));
        channels.add(new SMSChannel("+91-9876543210"));
        channels.add(new WhatsAppChannel("+91-9876543210"));

        for (NotificationChannel channel : channels) {
            channel.send("Your order #1234 has been shipped!");
            // JVM decides at runtime which send() to call based on actual object type
        }

        // Output:
        // 📧 EMAIL to alice@example.com: Your order #1234 has been shipped!
        // 📱 SMS to +91-9876543210: Your order #1234 has been shipped!
        // 💬 WHATSAPP to +91-9876543210: Your order #1234 has been shipped!
        NotificationChannel.broadcastAlert(new EmailChannel("a@b.com"), "Server is down!");
        NotificationChannel.broadcastAlert(new SMSChannel("+91-9999999999"), "Server is down!");
        // Same method, different objects — works because of upcasting + runtime polymorphism

        // explicity casting - ClassCastException — When Downcasting Goes Wrong
        // Animal a = new Cat();   // the actual object is a Cat

        // Dog d = (Dog) a;         // ClassCastException at RUNTIME!
                                    // Compiler allows it (both are Animals)
                                    // JVM sees: actual object is Cat, not Dog → CRASH

        // Animal a = getAnimalFromSomewhere();  // could be Dog, Cat, Bird — we don't know

        // Dangerous — assumes it's a Dog without checking:
        // Dog d = (Dog) a;  // might throw ClassCastException

        // Safe — check first:
        // if (a instanceof Dog) {
        //     Dog d = (Dog) a;
        //     d.fetch();
        //     System.out.println("It's a dog, it can fetch!");
        // } else if (a instanceof Cat) {
        //     Cat c = (Cat) a;
        //     c.purr();
        //     System.out.println("It's a cat, it can purr!");
        // } else {
        //     System.out.println("Unknown animal type: " + a.getClass().getSimpleName());
        // }


        // The Complete Picture — All Four Concepts Together
//----------------------------------------------------------------------------------------------
        // Animal animal;   // Reference variable — can hold any Animal subtype

        // // 1. UPCASTING (implicit, safe):
        // animal = new Dog("Rex");     // Dog IS-A Animal — automatic, no syntax

        // // 2. COMPILE-TIME check:
        // animal.makeSound();          // Compiler: "Does Animal have makeSound? YES → OK"

        // // 3. RUNTIME POLYMORPHISM (dynamic dispatch):
        //                             // JVM: "Actual object is Dog → call Dog.makeSound()"
        //                             // Output: "Woof!" — NOT "Some generic animal sound"

        // // 4. DOWNCASTING (explicit, risky):
        // if (animal instanceof Dog d) {    // safely check first
        //     d.fetch();                     // Dog-specific method — now accessible
        // }

        // // 5. ClassCastException scenario:
        // animal = new Cat("Whiskers");     // now holds a Cat
        // Dog d2 = (Dog) animal;            // ClassCastException — actual object is Cat, not Dog
    }
}
