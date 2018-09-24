package chapter5.section3;

import java.util.concurrent.*;

public class Launch {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCData> queue = new LinkedBlockingQueue<>();
        Producer p1 = new Producer(queue);
        Producer p2 = new Producer(queue);
        Producer p3 = new Producer(queue);
        Producer p4 = new Producer(queue);

        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(p4);

        service.execute(c1);
        service.execute(c2);
        service.execute(c3);

        Thread.sleep(1000);
        p1.stop();
        p2.stop();
        p3.stop();
        p4.stop();
        Thread.sleep(1000);

        service.shutdown();

        System.out.println("exit()");
    }
}
