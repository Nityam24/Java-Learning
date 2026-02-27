import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CloningClass implements Cloneable {
    String name;
    Manager manager;  // Reference type!

    public CloningClass(String name) {
        this.name = name;
        this.manager = new Manager();
        manager.setName(this.name);
    }

    /**
     * @param other
     */
    public CloningClass(CloningClass other) {
    this.name = other.name;
    this.manager = new Manager();  // Deep copy without Cloneable
    manager.setName(other.manager.name);
}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();  // Shallow copy — 'manager' is shared!
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        // CloningClass cc = new CloningClass("Jay");
        // System.out.println(cc.manager.name);

        // // Shallow Copy
        // CloningClass cc2 = (CloningClass) cc.clone();
        // cc2.manager.name = "bob";
        // System.out.println(cc2.manager.name);
        // System.out.println(cc.manager.name);


        // Deep Copy
        CloningClass cc3 = new CloningClass("Nit");
        System.out.println(cc3.manager.name);
        CloningClass cc4 = new CloningClass(cc3);
        System.out.println(cc4.manager.name);
        System.out.println(cc3.manager.name);
        cc4.manager.name = "Jayyyy";
        System.out.println(cc4.manager.name);
        System.out.println(cc3.manager.name);
        
    }
}

class Manager {
    public String name;
     
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/////////////////Rules for Creating an Immutable Class//////////////////////////////////////////////////////

final class Money {           // 1. Class is final — cannot be subclassed

    private final double amount;     // 2. Fields are private and final
    private final String currency;

    public Money(double amount, String currency) {  // 3. Constructor sets all fields
        this.amount = amount;
        this.currency = currency;
    }

    // 4. No setters — ever

    public double getAmount() {      // 5. Getters return values (safe for primitives)
        return amount;
    }

    public String getCurrency() {
        return currency;             // String is already immutable, safe to return
    }
}


///////////////////Handling Mutable Fields (like Lists or Dates)///////////////////////////////////////////////////////////
 final class Employee {
    private final List<String> skills;

    public Employee(List<String> skills) {
        this.skills = new ArrayList<>(skills);  // 6. Defensive copy on the way IN
    }

    public List<String> getSkills() {
        return Collections.unmodifiableList(skills);  // 7. Return unmodifiable view (or new copy)
    }
}