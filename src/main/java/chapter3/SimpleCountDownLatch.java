package chapter3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleCountDownLatch implements Runnable {
	public static final CountDownLatch end = new CountDownLatch(10);

	@Override
	public void run() {
		try {
			Thread.sleep(500);
			end.countDown();
			System.out.println(end.getCount());
		} catch (InterruptedException e) {}
	}

	public static void main(String[] s) throws InterruptedException {
		SimpleCountDownLatch simple = new SimpleCountDownLatch();
		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 9; i++) {
			service.submit(simple);
		}
		end.await();
		System.out.println("action");
		service.shutdown();
	}
}
