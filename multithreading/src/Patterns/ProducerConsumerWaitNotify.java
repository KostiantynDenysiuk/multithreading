package Patterns;

import java.util.LinkedList;
import java.util.Queue;

/*
Using wait() and notify() Methods:
This approach involves using a shared object for communication between
the producer and consumer threads. The wait() method is called by a thread
to release the monitor lock and wait until another thread invokes the notify() method.
*/

public class ProducerConsumerWaitNotify {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int MAX_SIZE = 10;

    public static void main(String[] args) {
        ProducerConsumerWaitNotify pc = new ProducerConsumerWaitNotify();

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
                synchronized (queue) {
                    while (queue.size() == MAX_SIZE) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    queue.offer(value++);
                    queue.notifyAll();
                    System.out.println("Produced: " + value);
                }
            }
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    int value = queue.poll();
                    queue.notifyAll();
                    System.out.println("Consumed: " + value);
                }
            }
        }
    }
}