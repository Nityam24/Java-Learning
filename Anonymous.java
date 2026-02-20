class InnerAnonymous {

    public InnerAnonymous() {
        System.out.println("insider constructor");
    }
    
    public void show() {
        System.out.println("show method");
    }
}


public class Anonymous {

    public static void main(String[] args) {
        new InnerAnonymous().show();
        new InnerAnonymous();


    }
}