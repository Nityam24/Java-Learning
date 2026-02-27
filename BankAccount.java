// Blueprint for a BankAccount
public class BankAccount {

    // Fields (data)
    String accountHolderName;
    double balance = 499;
    // Integer balance;

    // public BankAccount() {}

    // public BankAccount() {
    // balance = 0.0;
    // accountHolderName = "Unknown";
    // }

    public BankAccount() {
        this("Nityam", 1200);
    }

    public BankAccount(String accountHolderName, double balance) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // public BankAccount(BankAccount other) {
    // this.accountHolderName = other.accountHolderName;
    // this.balance = other.balance;
    // }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountHolderName == null) ? 0 : accountHolderName.hashCode());
        long temp;
        temp = Double.doubleToLongBits(balance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BankAccount other = (BankAccount) obj;
        if (accountHolderName == null) {
            if (other.accountHolderName != null)
                return false;
        } else if (!accountHolderName.equals(other.accountHolderName))
            return false;
        if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
            return false;
        return true;
    }

    ///// method overloading
    ///// //////////////////////////////////////////////////////////////////////////////

    public void set(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        // return this.accountHolderName;
        System.out.println("Account Hoilder Name with set: " + this.accountHolderName);
    }

    public void set(double balance) {
        this.balance = balance;
        // return this.balance;
        System.out.println("Balance with set: " + this.balance);
    }

    public BankAccount set(String accountHolderName, double balance) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        return this;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Method (behavior)
    void deposit(int amount) {
        balance += amount;
    }

    void displayBalance() {
        System.out.println("Balance: " + balance + " " + accountHolderName);
    }

    /////// argvars
    /////// ///////////////////////////////////////////////////////////////////////

    void sum(int... numbers) { // 'numbers' is treated as int[]
        int total = 0;
        for (int n : numbers)
            total += n;
        // return total;
        System.out.println("sum:" + total);
    }

    // void sum(Integer... numbers) { // 'numbers' is treated as int[] // Ambiguious
    // error
    // int total = 0;
    // for (int n : numbers)
    // total += n;
    // // return total;
    // System.out.println("sum:" + total);
    // }

    void sum(int num1, int num2) {
        int total = 0;
        total = num1 + num2;
        System.out.println("total:" + total);
    }

    //////// Argument Passing – Pass by Value
    //////// //////////////////////////////////////////////////////////

    static void changeBalance(BankAccount acc) {
        acc.balance = 9999; // Changes the ACTUAL object's state — reference is shared
    }

    static void reassign(BankAccount acc) {
        acc = new BankAccount("nityam", 1200); // Only changes local reference — original unchanged
        System.out.println("inside reassign:" + acc.accountHolderName + " " + acc.balance);
    }

    public static void main(String[] args) {
        // BankAccount alicesAccount = new BankAccount(); // Object created
        // alicesAccount.accountHolderName = "Alice";
        // alicesAccount.balance = 5000;
        // alicesAccount.deposit(1000);
        // alicesAccount.displayBalance(); // Output: Balance: 6000.0

        // BankAccount account = null; // Reference exists but points to nothing
        // account.deposit(100); // NullPointerException! — common interview question

        // BankAccount acc1 = new BankAccount();
        // BankAccount acc3 = new BankAccount();
        // BankAccount acc2 = acc1; // Both point to the SAME object

        // acc3.balance = 1000;
        // acc1.balance = 1000;
        // System.out.println(acc2.balance);
        // System.out.println(acc1.equals(acc2));
        // System.out.println(acc1 == acc2);
        // System.out.println(acc1.equals(acc3));
        // System.out.println(acc1 == acc3);

        // new BankAccount().displayBalance(); // Created and used immediately, no
        // reference kept

        ///// constructor practices

        // BankAccount acc = new BankAccount("Alice", 5000);
        // acc.displayBalance();

        // BankAccount original = new BankAccount("Alice", 5000);
        // original.displayBalance();

        // BankAccount copy = new BankAccount(original); // Independent copy
        // copy.displayBalance();

        // System.out.println(original == copy);
        // System.out.println(original.equals(copy));

        // BankAccount obj1 = new BankAccount("Jay", 2300);
        // obj1.displayBalance();

        // BankAccount obj2 = new BankAccount();
        // obj2.set(12);
        // obj2.set("nityam Patel");
        // obj2.set("Nityammmm", 12009);
        // obj2.displayBalance();
        // // obj2.sum(null); //NullPointer Exception
        // obj2.sum(2, 3, 5, -2);
        // obj2.sum(2, 3);

        BankAccount myAcc = new BankAccount("Alice", 100);
        changeBalance(myAcc);
        System.out.println(myAcc.balance); // 9999 — state changed!

        reassign(myAcc);
        System.out.println(myAcc.balance); // 9999 — original reference unchanged
    }
}