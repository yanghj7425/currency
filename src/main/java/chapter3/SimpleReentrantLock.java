package chapter3;

import java.util.concurrent.locks.ReentrantLock;

public class SimpleReentrantLock implements Runnable {
	public static ReentrantLock lock = new ReentrantLock();
	private  static int k = 0;

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			lock.lock();
			try {
				k++;
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SimpleReentrantLock reentrantLock = new SimpleReentrantLock();
		Thread t1 = new Thread(reentrantLock);
		Thread t2 = new Thread(reentrantLock);

		t1.start();

		t2.start();

		t1.join();

		t2.join();

		System.out.println(k);

	}

}
