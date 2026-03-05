// public class BankingApp {
//     public static void main(String[] args) {
//         // This IS the main thread — JVM created it automatically
//         System.out.println("Main thread: " + Thread.currentThread().getName());
//         // prints: Main thread: main

//         // From this main thread, you spawn additional threads
//         // to handle concurrent work
//     }
// }
// ```

// ### Process vs Thread
// ```
// PROCESS                                 THREAD
// ─────────────────────────────────────────────────────────────────
// Independent program in execution        Subset of a process
// Has its own memory space                Shares process memory (heap, static fields)
// Heavyweight — expensive to create       Lightweight — cheap to create
// Communication via IPC (sockets, pipes)  Communication via shared memory
// Crash in one doesn't affect others      Crash in one can bring down whole process
// Example: Chrome.exe, BankingApp.exe     Example: main thread, worker thread

// JVM Process memory:
// ┌──────────────────────────────────────────────┐
// │  Heap (shared by ALL threads)                │
// │  ┌────────────────────────────────────────┐  │
// │  │ BankAccount objects, Transaction data  │  │
// │  └────────────────────────────────────────┘  │
// │  Method Area / MetaSpace (shared)            │
// │  Thread 1 Stack │ Thread 2 Stack │ Thread 3  │
// │  (each thread   │ (private to    │ Stack      │
// │   has its own)  │ that thread)   │ (private)  │
// └──────────────────────────────────────────────┘


// WITHOUT multithreading — sequential processing
// public class SequentialBankProcessor {
//     public void processDailyOperations() {
//         processTransactions();      // 30 seconds
//         generateReports();          // 20 seconds
//         sendNotifications();        // 15 seconds
//         reconcileAccounts();        // 25 seconds
//         // Total: 90 seconds — each waits for the previous to finish
//     }
// }

// // WITH multithreading — concurrent processing
// public class ConcurrentBankProcessor {
//     public void processDailyOperations() throws InterruptedException {
//         Thread t1 = new Thread(() -> processTransactions());   // 30s
//         Thread t2 = new Thread(() -> generateReports());       // 20s
//         Thread t3 = new Thread(() -> sendNotifications());     // 15s
//         Thread t4 = new Thread(() -> reconcileAccounts());     // 25s

//         t1.start(); t2.start(); t3.start(); t4.start();

//         t1.join(); t2.join(); t3.join(); t4.join();
//         // Total: ~30 seconds (limited by the slowest task) — 3x faster!
//     }
// }
// ```

// **Key reasons for multithreading:**
// - **Performance** — utilize multiple CPU cores simultaneously
// - **Responsiveness** — keep UI/main thread responsive while background work runs
// - **Resource utilization** — while one thread waits for I/O, another can use the CPU
// - **Throughput** — serve multiple bank customers/requests at the same time

// ### Process-based vs Thread-based Multitasking
// ```
// Process-based multitasking:
// Running BankingApp.exe AND BrowserApp.exe simultaneously
// OS switches between processes — heavyweight context switch

// Thread-based multitasking:
// BankingApp.exe running TransactionThread AND ReportThread simultaneously
// JVM switches between threads — lightweight context switch
// Threads share same heap — no data copying between them




public class Multi_Thread {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();

        System.out.println("Name: " + mainThread.getName());       // main
        System.out.println("Priority: " + mainThread.getPriority()); // 5 (NORM_PRIORITY)
        System.out.println("Is daemon: " + mainThread.isDaemon());   // false

        // Main thread is NOT a daemon — JVM waits for it to finish
        // All non-daemon threads must complete before JVM exits

        // Main thread spawns child threads
        Thread worker = new Thread(() -> System.out.println("Worker running"));
        worker.start();

        System.out.println("Main thread continues...");
        // JVM exits only after ALL non-daemon threads finish
    }
}
