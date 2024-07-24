package IOBound;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IoBoundApplication {
    private static final int NUMBER_OF_TASKS = 10_000; //Make more threads than OS can bear

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Press enter to start");
        s.nextLine();
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        //create a dynamic thread pool
        //try (ExecutorService executorService = Executors.newCachedThreadPool()) { //unfixed sized ThreadPool
        try (ExecutorService executorService = Executors.newFixedThreadPool(1000)) { //Prevent OS from creating too many threads
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                //executorService.submit(() -> blockingIoOperation());

                //check the context switches operations cost
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 100; j ++ ){
                            blockingIoOperation();
                        }
                    }
                });
            }
        }
    }


    //simulates a long blocking IO
    private static void blockingIoOperation() {
        System.out.println("Executing a blocking task from thread " + Thread.currentThread());
        try {
            //Thread.sleep(1000);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
