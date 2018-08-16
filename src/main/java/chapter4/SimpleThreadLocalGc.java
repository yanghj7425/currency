package chapter4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadLocalGc {

    private static volatile ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + "is gc");
        }
    };
    private static volatile CountDownLatch downLatch = new CountDownLatch(1000);


    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (tl.get() == null) {
                    tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                        @Override
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + "is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId() + ": create thread");
                }
                Date t = tl.get().parse("2018-08-18 23:78:43");
                System.out.println(t);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                downLatch.countDown();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new ParseDate(i));
        }
        downLatch.await();

        System.out.println("mission complete!!");
        tl = null;


        System.gc();

        System.out.println("first GC complete !!");

        tl = new ThreadLocal<>();
        downLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new ParseDate(i));
        }
        downLatch.await();

        System.gc();

        System.out.println("second GC complete!!");


        executorService.shutdown();
    }

}
