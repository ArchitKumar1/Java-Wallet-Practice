import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    // Maximum number of threads in thread pool
    static final int MAX_THREADS = 5;
    static final int MAX_TASKS = 10;

    public static void main(String[] args) {
        // creates five tasks
        List<Runnable> runnableList = new ArrayList<>();

        for (int i = 0; i < MAX_TASKS; i++) {
            Runnable runnable = new Task("task " + i);
            runnableList.add(runnable);
        }

        // creates a thread pool with MAX_T no. of
        // threads as the fixed pool size(Step 2)
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);

        // passes the Task objects to the pool to execute (Step 3)
        for (Runnable runnable : runnableList) {
            pool.execute(runnable);
        }

        // pool shutdown ( Step 4)
        pool.shutdown();
    }
}