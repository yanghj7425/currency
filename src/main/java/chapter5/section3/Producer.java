package chapter5.section3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private volatile boolean isRunning;
    private BlockingQueue<PCData> queue;
    private static AtomicInteger count = new AtomicInteger();

    private static final int SELEEPTIME = 1000;


    public Producer(BlockingQueue<PCData> queue) {
        this.queue = queue;
        isRunning = true;
    }

    @Override
    public void run() {
        PCData data = null;
        Random random = new Random();
        System.out.println("This Thread Id is : " + Thread.currentThread().getId());
        try {
            while (isRunning) {
                Thread.sleep(random.nextInt(SELEEPTIME));
                data = new PCData(count.incrementAndGet());
                System.out.println(data + " is put into queen");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("failed to put data : " + data);
                }
            }

            System.out.println("exit");
        } catch (Exception e) {

        }
    }


    public void stop() {
        isRunning = false;

        System.out.println(Thread.currentThread().getId() + " running status is :" + isRunning);
    }
}
