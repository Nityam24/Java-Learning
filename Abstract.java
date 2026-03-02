import java.lang.Math;

class Objects {
    private String shade;

    Objects(String shade) {
        this.shade = shade;
    }

    void displayShade() {
        System.out.println("This is shade: " + this.shade);
    }
}

abstract class Shape extends Objects {

    // Concrete field — all shapes have a color
    String color;

    // Concrete constructor — can have constructors
    public Shape(String shade, String color) {
        super(shade);
        this.color = color;
    }

    // Concrete method — same for ALL shapes
    void displayColor() {
        System.out.println("Color: " + color);
    }

    // Abstract method — NO body, each shape draws differently
    abstract double calculateArea(); // No implementation — child MUST provide it

    abstract double calculatePerimeter();

    final void processPayment() {
        boolean success = true;
        // return success;
        System.out.println(success);
    }
}

class Circle extends Shape {
    private final static double PI = 3.14;
    private int radius;

    Circle(int radius, String shade, String color) {
        super(shade, color);
        this.radius = radius;
    }

    double calculateArea() {
        System.out.println("Calculate Area");
        return PI * Math.pow(this.radius, 2);
    }

    double calculatePerimeter() {
        System.out.println("calculatePerimeter");
        return 2 * PI * this.radius;
    }

    void CircleSpecific() {
        System.out.println("this is only present in circle class");
    }
}

public class Abstract {
    public static void main(String[] args) {
        ////////////// Initialization of Abstract class //////////////
        ////////////// //////////////////////////////////////////
        // Shape ab = new Shape("Red"); //COMPILE ERROR — abstract class cannot be
        ////////////// instantiated

        // Can have both abstract AND concrete methods (unlike interfaces before Java 8)
        // Can have constructors (called via super() from child)
        // Can have instance variables of any type
        // Can have static methods
        // If a subclass doesn't implement all abstract methods, the subclass must also
        // be abstract

        Shape circle = new Circle(2, "light", "Yellow");
        circle.calculateArea();
        circle.calculatePerimeter();
        circle.displayColor();
        circle.processPayment();
        circle.displayShade(); // abstract class can extend normal java class also and add its own abstract
                               // method which further derived by child class
        Circle circle2 = (Circle) circle;
        circle2.CircleSpecific();
    }
}
