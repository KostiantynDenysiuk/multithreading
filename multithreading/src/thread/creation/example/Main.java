package thread.creation.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//       Thread thread = new Thread(new Runnable() {
//           @Override
//           public void run() {
//               //code that will run in a new thread
////               System.out.println("We are now in thread  " + Thread.currentThread().getName());
////               System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
//
//               throw new RuntimeException("Intentional Exception");
//           }
//       });
//
////       thread.setName("New Worker Thread");
////       thread.setPriority(Thread.MAX_PRIORITY);
////       System.out.println("We are in thread " + Thread.currentThread().getName() + " before we started a new thread");
////       thread.start();
////        System.out.println("We are in thread " + Thread.currentThread().getName() + " after we started a new thread");
//
////        Thread.sleep(10000);
//
//        thread.setName("Misbehaved thread");
//        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                System.out.println("A critical error happened in thread " + t.getName()
//                + " the error is " + e.getMessage());
//            }
//        });
//
//        thread.start();

        Thread thread = new NewThread();
        thread.start();
    }

    private static class NewThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello from " + Thread.currentThread().getName());
        }
    }

}