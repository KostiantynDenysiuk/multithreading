package thread.coordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinMethod {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(1000000L, 3435L, 2324L, 4656L, 23L, 2435L, 5566L);
        //we want to calculate factorials for all list members

        List<FactorialThread> threads = new ArrayList<>();

        for (Long number : inputNumbers) {
            FactorialThread factorialThread = new FactorialThread(number);
            threads.add(factorialThread);
        }

        for (Thread thread : threads) {
            thread.setDaemon(true); //to exit the app even when one thread still calculates unappropriated big number
            thread.start();
        }

        //For every thread the join method will return only when thread is terminated
        //and by the time the main thread finishes this loop, all the factorial threads
        //are guaranteed to have finished.
        for (Thread thread : threads) {
            //in case of very big number to calculate we limited time of work for each thread
            thread.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("Calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }

    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;
            for (long i = n; i > 0 ; i-- ) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }

        public  boolean isFinished() {
            return  isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
