package VirtualThreadCreation;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreads {
    //This CPU has only 4 cores that's why max number of workers (which is platform threads)
    // equals 4. We can see that virtual thread after its sleeping could be mounted on another worker thread.

    private static final int NUMBER_OF_VIRTUAL_THREADS = 100;

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () ->System.out.println("Inside thread: " + Thread.currentThread());
        Thread platformThread = Thread.ofPlatform().unstarted(runnable);
        platformThread.start();
        platformThread.join();

        List<Thread> virtualThreads = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            //virtualThreads.add(Thread.ofVirtual().unstarted(runnable));
            virtualThreads.add(Thread.ofVirtual().unstarted(new BlockingTask()));
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.start();
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.join();
        }

    }

    private static class BlockingTask implements Runnable {
        @Override
        public void run() {
            System.out.println("Inside thread: " + Thread.currentThread() + " before blocking call");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Inside thread: " + Thread.currentThread() + " after blocking call");
        }
    }
}
