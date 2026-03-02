// public class Employee extends Object { }  // Implicit


// Parent class — written once

class Employee {
    protected String name = "";
    int employeeId;
    double salary;

    Employee(String name) {
        this.name = name;
    }

    void work() {
        System.out.println(name + " is working in employee class.");
    }

    void getSalary() {
        System.out.println("Salary: " + salary);
    }

    // Overriding
    protected void login() { }

    // final method can't be overriden 
    public final void freeze() {  // Security-critical — cannot be altered by child
        boolean isActive = false;
        System.out.println(isActive);
    }

    // static methods — Hiding, NOT Overriding
    static void display() { 
        System.out.println("Parent static"); 
    }

    // covariamt type is allowed in java inheritance for overriding
    public Employee create() {
        return new Employee("employeeeeee");
    } 

    // Dynamic nmethod dispatch - runtime polymorphism
    void sound() {
        System.out.println("Employee makes sound");
    }

    // private method try to access outside class
    private void doIt() {
        System.out.println("Do It from parent - Employee");
    }
}

// Child class — gets everything from Employee for free
class Manager extends Employee {
    int teamSize;
    String name;

    Manager(String empName, String name) {
        super(empName);
        this.name = name;
        // super.name = "Employee Name";
    }
    
    void conductReview() {
        // this.name = "Jay";
        System.out.println(name);
        System.out.println(this.name);
        System.out.println(super.name + " is conducting a performance review.");
        // 'name' is inherited from Employee — no need to redeclare it
    }

    // Overriding parent method 
    @Override
    void work() {
        super.work();
        System.out.println(this.name + " is working in Manager class.");
    }

    // valid as we widen scope (protected to public)
    public void login() { }

    // not valid as we narrow scope (protected to private)
    // private void login() { }

    // Final method can't be Overriden
    //  public void freeze() { }  // COMPILE ERROR — cannot override final 

    // static methods — Hiding, NOT Overriding
     static void display() { 
        System.out.println("Child static"); 
    }

    // covariant type
    public Manager create() {
        return new Manager("Employee", "Manager");
    } 

    void sound() {
        System.out.println("Manager makes sound");
    }
}

class SubManager extends Manager {

     private String subName = "default sub name";

    SubManager(String empName, String ManName, String subName) {
        super(empName, ManName);
        this.subName = subName;
        System.out.println(empName + " " + ManName + " " + this.subName);
    }

}

class TeamLead extends Employee {
    private String name;

    TeamLead (String empName, String name) {
        super(empName);
        this.name = name;
        System.out.println(super.name + " " + this.name);
    }
}

//////////////////////////////// multiple inheritance in java ///////////////////////////////////

// public class A { void hello() { System.out.println("Hello from A"); } }
// public class B { void hello() { System.out.println("Hello from B"); } }

// public class C extends A, B { }  //  COMPILE ERROR — Java doesn't allow this

public class Inheritance {
    /**
     *
     */
    final static int num = 10;

    public Inheritance() {
    }

    public static void main(String[] args) {
        // Usage
        // Employee emp = new Employee("John");
        // // emp.name = "jay";
        // Manager mgr = new Manager("Employee Name", "Manager Name");
        // emp.name = "Nityam patel";
        // mgr.name = "Alice";
        // mgr.salary = 120000;
        // mgr.teamSize = 10;
        // mgr.work(); // Inherited from Employee → "Alice is working."
        // mgr.conductReview(); // Own method → "Alice is conducting a performance review."

        ///// Multi-level Inheritance //////////////////////////////////////////
        // SubManager sub = new SubManager("Employee", "Manager", "Sub Manager");

        ///// Hierarchical Inheritance — A → B, A → C //////////////////////////////////
        // TeamLead tm = new TeamLead("Employee Name", "Team Lead Name");

        /////OVERRIDE //////////////////////////////////////////////////////////////
        // Manager man = new Manager("empName", "Manager Name");
        // man.work();

        ////////////////// static methods — Hiding, NOT Overriding///////////////////////////////////
        
        // Employee.display(); // parent static
        // Manager.display(); // child static

        // Employee emp1 = new Manager("empName", "manager");
        // emp1.display(); // parent child

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Manager emp2 = new Manager("EMploye1", "Manager1");
        // Manager man = emp2.create();        //Returns Manager directly - no (Manager) cast needed

        // // Without covariant return types (old Java way):
        // Employee animal = emp2.create();
        // Manager man2 = (Manager) animal;  // Had to cast — ugly and risky

        /////////////////////// Object class (parent of all class) ////////////////////////////////////
        // these are some of main methods by object class
        // 1. toString() — String representation of the object
        // 2. equals() — Value comparison
        // 3. hashCode() — Integer hash for use in HashMap/HashSet (always override with equals)
        // 4. getClass() — Runtime type information
        // 5. clone() — Create a copy (requires Cloneable — covered in Module 6)
        // 6. wait(), notify(), notifyAll() — Thread coordination (used in multithreading scenarios).
        // 7. finalize() — Deprecated. Called before GC. Do not rely on this.

        /////////////////////////// Dynamic Method Dispatch //////////////////////////////////////////////
        Employee emp3 = new Manager("empNmae", "Manager");
        emp3.sound();               // manager makes sound

        // For calling parent method using parent reference and child object, we have to use super and called parent method inside overridden method

        // Manager emp4 = new Employee("empNmae");         // this gives compile error of Type mismatch: cannot convert from Employee to ManagerJava 
        // emp4.sound();

        Employee emp5 = new Manager("emp", "man");
        // emp5.conductReview();           // compile error - The method conductReview() is undefined for the type Employee

        // for above to happen - if we want to call child method with parent object (which is not in parent class)
        Manager emp6 = (Manager) emp5;
        emp6.conductReview();           // By cast it down (to child class) we can access its method

        //////////////////////// parent private method or variable access with parent ref and child object/////////////////////////////
        
        // Employee emp7 = new Employee("Nityammm");
        // emp7.doIt();            // method not visible in this case

        // Employee emp8 = new Manager("Emp", "Man");
        // emp8.doIt();             // Method not visible in this case also
    }
}
