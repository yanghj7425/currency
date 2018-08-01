package chapter3;

import java.util.concurrent.locks.ReentrantLock;

public class SimpleTryLock implements Runnable {

	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();
	boolean isStop = false;
	int lockToken = 0;

	public SimpleTryLock(int lockToken) {
		this.lockToken = lockToken;
	}

	@Override
	public void run() {
		int threadId = (int) Thread.currentThread().getId();
		if (lockToken == 1) {
			while (!isStop) {
				if (lock1.tryLock()) {
					try {
						out(threadId + " get lock1");
						Thread.sleep(200);
						if (lock2.tryLock()) {
							try {
								System.out.println(threadId + " : my job done 1");
								isStop = true;
							} finally {
								lock2.unlock();
								out(threadId + "free lock2");
							}
						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} finally {
						lock1.unlock();
						out(threadId + " free lock1");
					}

				}
			}
		} else {
			while (!isStop) {
				Thread.yield();
				if (lock2.tryLock()) {
					out(threadId + " get lock2 2");
					try {
						Thread.sleep(200);
						if (lock1.tryLock()) {
							out(threadId + " get lock1");
							try {
								System.out.println(Thread.currentThread().getId() + " : my job done 2");
								isStop = true;
							} finally {
								out(threadId + "free lock1");
								lock1.unlock();
							}
						}

					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} finally {
						out(threadId + "free lock2 2");
						lock2.unlock();
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new SimpleTryLock(1));
		Thread t2 = new Thread(new SimpleTryLock(2));
		t1.start();
		t2.start();
	}

	public void out(Object o) {
	//	System.out.println(o);
	}

}
