package chapter3;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *   默认 ThreadPoolExecutor  中提供了空的  beforeExecute， afterExecute 可以扩展来跟踪线程的执行情况
 */
public class SimpleExtendThreadPool {

    public static class MyTask implements Runnable {
        private String threadName;

        public MyTask(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            System.out.println("executing ... " + " \t thread Id" + Thread.currentThread().getId() + " \t Task name" + getThreadName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private String getThreadName() {
            return threadName;
        }
    }


    public static void main(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("ready ..." + ((MyTask) r).getThreadName());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("end \t" + ((MyTask) r).getThreadName());
            }

            @Override
            protected void terminated() {
                System.out.println("terminated " + Thread.currentThread().getName());
            }
        };

//        for (int i = 0; i < 15; i++) {
//            MyTask myTask = new MyTask("task" + i + (System.currentTimeMillis() / 274));
//
//            service.execute(myTask);
//
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        service.shutdown();
//

        System.out.println(Runtime.getRuntime().availableProcessors());

    }
}
