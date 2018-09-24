package chapter5.section3;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<PCData> queue;
    private static final int SELEEPTIME = 1000;
    private boolean isRunning;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
        isRunning = true;
    }

    @Override
    public void run() {
        System.out.println("Start consumer thread id is : " + Thread.currentThread().getId());
        Random random = new Random();
        try {
            while (isRunning) {
                PCData data = queue.take();
                if (data != null) {
                    int re = data.getInData() * data.getInData();
                    System.out.println(MessageFormat.format("{0} * {1} = {2}", data.getInData(), data.getInData(), re));
                    Thread.sleep(random.nextInt(SELEEPTIME));
                } else {
                    System.out.println("the queen is null");
                }
            }
        } catch (InterruptedException e) {

        }


    }
}
