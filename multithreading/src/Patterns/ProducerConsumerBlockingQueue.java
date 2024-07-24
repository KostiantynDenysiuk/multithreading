package Patterns;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
Using BlockingQueue:
Java's java.util.concurrent package provides the BlockingQueue interface,
which handles all synchronization internally, making the implementation simpler and more robust.
 */

public class ProducerConsumerBlockingQueue {
    private static final int MAX_SIZE = 10;
    private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(MAX_SIZE);

    public static void main(String[] args) {
        ProducerConsumerBlockingQueue pc = new ProducerConsumerBlockingQueue();

        Thread producerThread = new Thread(pc.new Producer());
        Thread consumerThread = new Thread(pc.new Consumer());

        producerThread.start();
        consumerThread.start();
    }

    private class Producer implements Runnable {
        @Override
        public void run() {
            int value = 0;
            while (true) {
                try {
                    queue.put(value);
                    System.out.println("Produced: " + value);
                    value++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    int value = queue.take();
                    System.out.println("Consumed: " + value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

