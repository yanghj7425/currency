package chapter3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SimpleReadWriteLock {

	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private static Lock readLock = readWriteLock.readLock();

	private static Lock writeLock = readWriteLock.writeLock();

	private int value = 0;

	public Object handleRead(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(500);
			System.out.println(value);
			return value;
		} finally {
			lock.unlock();
		}
	}

	public Object handleWrite(Lock lock) throws InterruptedException {
		try {
			lock.lock();
			Thread.sleep(500);
			value++;
			System.out.println(value);
			return value;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		final SimpleReadWriteLock simple = new SimpleReadWriteLock();
		Runnable readRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					simple.handleRead(readLock);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("ReadThread exit");
			}

		};

		Runnable writeRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					simple.handleWrite(writeLock);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("WriteThread exit");
			}

		};

		for (int i = 0; i < 10; i++) {
			new Thread(readRunnable).start();
		}

		for (int i = 10; i > 0; i--) {
			new Thread(writeRunnable).start();
		}

	}
}
