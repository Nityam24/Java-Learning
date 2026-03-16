import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodRefs {
    /*
     * A method reference is shorthand for a lambda that just calls a method.
     * Instead of x -> x.doSomething(), you write ClassName::doSomething. The ::
     * operator is the method reference operator.
     * 
     * When to use it: If your entire lambda body is just
     * "call this existing method with the given arguments", a method reference is
     * cleaner and more readable.
     */

    public static void main(String[] args) {
        List<Integer> orders = new ArrayList<>(Arrays.asList(1, 6, 9, 4, 3, 6, 8));

        // Lambda form
        // orders.forEach(order -> System.out.println(order));

        // Method reference form — exactly equivalent
        orders.forEach(System.out::println);

        // =============================================================================================================================
        /*
         * Static method Class::staticMethod x -> Class.staticMethod(x)
         * Instance — specific object instance::method x -> instance.method(x)
         * Instance — arbitrary object Class::instanceMethod x -> x.method()
         * Constructor Class::new x -> new Class(x)
         */

        /*
         * Use a method reference when the lambda body contains nothing except a single
         * method call, and the arguments match exactly. If you need to transform the
         * argument, add logic, or the method signature doesn't align — stick with a
         * lambda.
         */
    }
}
