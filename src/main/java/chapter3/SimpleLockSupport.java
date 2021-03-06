package chapter3;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞类工具 LockSupport
 */
public class SimpleLockSupport {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String threadName) {
            super.setName(threadName);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                /**
                 * 阻塞当前线程，阻塞后线程是 WAITING 状态，不会像 suspend  处于 RUNNABLE 状态
                 */
                LockSupport.park();
            }
            System.out.println(getName() + "\texit");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);

        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
