public class Employee {
    static int employeeCount = 0; // Shared across ALL Employee objects
    static String dbUrl;
    String name;
    final static double PI = 3.14159;
    static double RI = 2.1354;
    final double TI = 8.99;

    final double radius; // blank final

    static {
        // This runs ONCE when the class loads
        dbUrl = "jdbc:mysql://localhost/mydb";
        System.out.println("Config loaded.");
        // System.out.println(name); // compiler error
        // PI = 3.0; // COMPILE ERROR
        RI = 4.55;
        System.out.printf("RI: %.2f", RI);
        // TI = 3.676; compiler error
    }

    public Employee(double radius) {
        this.radius = radius; // OK — initialized in constructor
        System.out.println(radius);
    }

    public Employee(String name) {
        this.radius = 8;
        // // static variable can be initialize either during
        // declaration or constructor (should be in each constructor) and can't
        // initialize by static method nore instance metjod
        this.name = name;
        employeeCount++; // Every time a new Employee is created, this increments
        square(employeeCount);
    }

    static void square(int n) {
        // return n * n;
        System.out.println(n * n);
    }

    // static void radiusAssign(double radius) {
    // this.radius = radius; // comiler error
    // }

    // void radiusAssign(double radius) {
    // this.radius = radius; // comiler error
    // }

    void doSomething() {
        Employee temp = new Employee("Temp");
    } // 'temp' goes out of scope here — object eligible for GC

    public static void main(String[] args) {

        // Employee e1 = new Employee("Alice");
        // Employee e2 = new Employee("Bob");
        Employee e3 = new Employee(4);
        // e3.radiusAssign(4);
        System.out.println(Employee.employeeCount); // 2 — accessed via class name

        // Employee.square(employeeCount);
        // System.out.println(Employee.dbUrl);

        //////////////// Garbage Collector ///////////////////////////////////////
        Employee emp = new Employee("Alice");
        emp = null; // Object is now eligible

        Employee emp1 = new Employee("Alice");
        emp1 = new Employee("Bob"); // Alice object is now eligible

        // a.friend = b;
        // b.friend = a;
        // a = null;
        // b = null; // Both objects eligible — they only reference each other
    }
}
