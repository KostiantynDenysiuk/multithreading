package thread.coordination;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new BlockingClass());
        thread.setName("blocking thread");
        thread.start();
        thread.interrupt();

        Thread thread1 = new Thread(new LongComputationalTask(new BigInteger("2000"), new BigInteger("100000000")));
        thread1.setName("Computational thread");
        //do not block our application to come to the end
        thread1.setDaemon(true);
        thread1.start();
        thread1.interrupt();


    }

    public static class BlockingClass implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                throw new RuntimeException("Exiting blocking thread");
            }
        }
    }

    private static class LongComputationalTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationalTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("Prematurely interrupted computation");
//                    return BigInteger.ZERO;
//                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
