package chapter3;

import java.util.concurrent.*;

public class SimpleExceptionThreadPool {

    private static class DivTask implements Runnable {
        int a, b;

        public DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            double div = a / b;
            System.out.println(div);
        }
    }

    /**
     * 定位异常栈
     */
    private static class TraceThreadExceptionPool extends ThreadPoolExecutor {

        public TraceThreadExceptionPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        public void execute(Runnable command) {
            super.execute(warp(command, clientTrace(), Thread.currentThread().getName()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(warp(task, clientTrace(), Thread.currentThread().getName()));
        }

        private Exception clientTrace() {
            return new Exception("Client stack trace");
        }

        private Runnable warp(final Runnable task, final Exception clientStack, String clientThreadName) {
            return new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } catch (Throwable throwable) {
                        clientStack.printStackTrace();
                        throw throwable;
                    }
                }
            };
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());
        /**
         * 直接用 submit 提交会吞食异常信息,需要获取 future 返回值 并 get
         * 或者 改为 execute 但是仍然不能看出任务在哪儿提交的
         */
        for (int i = 0; i < 5; i++) {
            Future<?> future = pools.submit(new DivTask(100, i));
            future.get();
        }
        System.out.println("************************");

        /**
         * 会显示出该任务在何处被提交
         */
        TraceThreadExceptionPool traceThreadExceptionPool = new TraceThreadExceptionPool(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            traceThreadExceptionPool.submit(new DivTask(100, i));
        }
    }
}
