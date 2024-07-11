package threadcreation2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        Thread firstTread = new NewThread();
        firstTread.setName("first thread");
        //firstTread.start();

        Thread secondTread = new Thread(()-> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Inside the second thread");
        });

        secondTread.setName("second thread");

        Thread thirdThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Inside the third thread");
            }
        });

        thirdThread.setName("third thread");

        threads.add(firstTread);
        threads.add(secondTread);
        threads.add(thirdThread);

        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class NewThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Inside the first thread");
        }
    }
}
