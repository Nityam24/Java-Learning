// Parent class — written once
class Employee {
    String name = "";
    int employeeId;
    double salary;

    void work() {
        System.out.println(name + " is working.");
    }

    void getSalary() {
        System.out.println("Salary: " + salary);
    }
}

// Child class — gets everything from Employee for free
class Manager extends Employee {
    int teamSize;
    String name;
    
    void conductReview() {
        // this.name = "Jay";
        System.out.println(name);
        System.out.println(this.name);
        System.out.println(super.name + " is conducting a performance review.");
        // 'name' is inherited from Employee — no need to redeclare it
    }
}

public class Inheritance {
    /**
     *
     */
    final static int num = 10;

    public Inheritance() {
    }

    public static void main(String[] args) {
        // Usage
        Employee emp = new Employee();
        // emp.name = "jay";
        Manager mgr = new Manager();
        emp.name = "Nityam patel";
        mgr.name = "Alice";
        mgr.salary = 120000;
        mgr.teamSize = 10;
        mgr.work(); // Inherited from Employee → "Alice is working."
        mgr.conductReview(); // Own method → "Alice is conducting a performance review."

    }
}
