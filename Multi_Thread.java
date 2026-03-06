// import java.lang.*;

class ThreadMethod extends Thread {
    ThreadMethod(String Name) {
        super(Name);
    }

    @Override
    public void run() {
        System.out.println("Thread is running!");
        for (int i = 0; i < 10; i++) {
            // System.out.println(Thread.currentThread().getName() + " - Priority " +
            // Thread.currentThread().getPriority());
            String ch = null;
            try {
                ch = ch + "AB";
                // System.out.println("The thread is running");
                // Thread.sleep(2000);
                // System.out.println(i);
                System.out.println(
                        Thread.currentThread().getName() + " - Priority " + Thread.currentThread().getPriority());
                Thread.yield();
            } catch (Exception e) {
                // TODO: handle exception
                throw new RuntimeException("This is Interrupted Thread " + e);
            }
        }
    }
}

public class Multi_Thread {
    public static void main(String[] args) throws InterruptedException {

        // State of Thread: ///////////////////////////////////////////////////
        // Main Thread States

        // NEW

        // RUNNABLE

        // BLOCKED

        // WAITING

        // TIMED_WAITING

        // TERMINATED

        // Structure of State of THread Execution
        // NEW
        // |
        // | start()
        // v
        // RUNNABLE
        // / | \
        // / | \
        // BLOCKED WAITING TIMED_WAITING
        // \ | /
        // \ | /
        // RUNNABLE
        // |
        // v
        // TERMINATED

        Thread mainThread = Thread.currentThread();

        System.out.println("Name: " + mainThread.getName()); // main
        System.out.println("Priority: " + mainThread.getPriority()); // 5 (NORM_PRIORITY)
        System.out.println("Is daemon: " + mainThread.isDaemon()); // false

        // Main thread is NOT a daemon — JVM waits for it to finish
        // All non-daemon threads must complete before JVM exits

        // Main thread spawns child threads
        // Thread worker = new Thread(() -> System.out.println("Worker running"));
        // worker.start();

        // System.out.println("Main thread continues...");
        // JVM exits only after ALL non-daemon threads finish

        // thread methods
        // ThreadMethod t1 = new ThreadMethod("Nityam");
        // ThreadMethod t2 = new ThreadMethod("Nityam22");
        // ThreadMethod t3 = new ThreadMethod("Nityam33");

        // System.out.println(Thread.currentThread().getName());
        // t1.setPriority(Thread.MIN_PRIORITY);
        // t2.setPriority(Thread.NORM_PRIORITY);
        // t3.setPriority(Thread.MAX_PRIORITY);

        // t1.start();
        // t2.start();
        // t3.start();

        // System.out.println(Thread.currentThread().getState());
        // System.out.println("called!");
        // t1.join();
        // t2.join();

        // ThreadMethod th1 = new ThreadMethod("Heet");
        // th1.start();
        // th1.interrupt();

        ThreadMethod tr1 = new ThreadMethod("neet");
        ThreadMethod tr2 = new ThreadMethod("neeiiii");
        tr1.start();
        tr2.start();

        System.out.println("Hello");

        // Learn about THread Lock, Reenterant Lock, ReadWrite Lock, stamped Lock, etc
        // Learn dead Lock condition - cause, reason, method to resolve, etc
        // from claude docs and youtube https://youtu.be/4aYvLz4E1Ts?si=tNU3GktZDQswCXk5

        

    }
}
