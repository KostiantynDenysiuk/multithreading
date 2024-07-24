package IOBound;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
When we use thread-per-task model with a cached thread pool, our application crashed
because the OS refused to allow us to allocate more platform threads;

In this case we replace dynamically growing pool of platform threads
with another executor service implementation
which is called new virtual thread-per-task executor.
Executors.newFixedThreadPool() => Executors.newVirtualThreadPerTaskExecutor().

This executor service implementation creates a new virtual thread for every task we submit to it;

Executors.newFixedThreadPool() - much longer due to context switching among
very large number of platform threads, a situation which we called thrashing

Executors.newVirtualThreadPerTaskExecutor() the performance improvement is massive!

Also we broke blocking call into 100 shorter blocking calls and performance degraded.
This illustrates to us that while the overhead of mounting and unmounting of virtual threads is much
smaller than context switches, it is still not entirely negligible.
 */

public class IOBoundWithVirtualThreads {
    private static final int NUMBER_OF_TASKS = 10_000; //Make more threads than OS can bear

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        //create a dynamic thread pool
        //try (ExecutorService executorService = Executors.newCachedThreadPool()) { //unfixed sized ThreadPool
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) { //Prevent OS from creating too many threads
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
