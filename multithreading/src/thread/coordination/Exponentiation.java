package thread.coordination;
import java.math.BigInteger;

//These two examples shows how to stop the thread by using external exception.

public class Exponentiation {
    public static void main(String[] args) {

        //Thread can handle interrupt exception due to try-catch block
        Thread thread = new Thread(new BlockingClass());
        thread.setName("blocking thread");
        thread.start();
        thread.interrupt();


        //Thread has explicit interrupting of the exception handler (otherwise it won't stop)
        Thread thread1 = new Thread(new LongComputationalTask(new BigInteger("2000"), new BigInteger("100000000")));
        thread1.setName("Computational thread");
        thread1.setDaemon(true);
        thread1.start();
        thread1.interrupt();
    }

    private static class BlockingClass implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " goes to sleep!");
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
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + " - Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
