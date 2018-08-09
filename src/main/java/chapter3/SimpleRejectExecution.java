package chapter3;

import java.util.concurrent.*;


/**
 * 自定义线程池和拒绝策略
 */
public class SimpleRejectExecution {
    private static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + " Thread Id :" + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new RejectedExecutionHandler() {
            /**
             * 自定义拒绝策略，当队列满时不抛出异常，只记录信息
             * @param r
             * @param executor
             */
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(r.toString() + " is discard");
            }
        });

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            service.submit(myTask);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
