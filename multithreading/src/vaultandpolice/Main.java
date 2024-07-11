package vaultandpolice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int MAX_PASSWORD = 5999;

    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendHackerThread(vault));
        threads.add(new DescentHackerThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread : threads) {
            thread.start();
        }


    }

    private static class Vault {
        private int password;

        public Vault(int thePassword) {
            this.password = thePassword;
        }

        public boolean isCorrectPassword(int guess) throws InterruptedException {
            Thread.sleep(5);
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class DescentHackerThread extends HackerThread {
        public DescentHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                try {
                    if (vault.isCorrectPassword(guess)) {
                        System.out.println(this.getName() + "  guessed the password "  + guess);
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class AscendHackerThread extends HackerThread {
        public AscendHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess <= MAX_PASSWORD; guess++) {
                try {
                    if (vault.isCorrectPassword(guess)) {
                        System.out.println(this.getName() + "  guessed the password "  + guess);
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(i);
            }
            System.out.println("Game over for you hackers");
            System.exit(0);
        }

    }


}
