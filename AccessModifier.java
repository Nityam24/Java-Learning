public class AccessModifier {
    private int a;
    public int b;

    private void sum (int a, int b) {
        this.a = a;
        this.b = b;

        int total = 0;
        total = this.a + this.b;
        System.out.println(total);
    }

    private void sum() {
        // this.a = 10;
        // this.b = 20;

        int total = 0;
        total = this.a + this.b;
        System.out.println(total);
    }

    public static void main(String[] args) {
        AccessModifier obj3 = new AccessModifier();
        // obj3.sum(1,2);
        obj3.a = 20;
        obj3.sum();
    }
}
