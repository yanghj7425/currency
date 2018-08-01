package chapter3;

import java.util.concurrent.locks.ReentrantLock;

public class SimpleInterruptReentrantLock implements Runnable {
	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();

	int lockOrder;

	public SimpleInterruptReentrantLock(int lockOrder) {
		this.lockOrder = lockOrder;
	}

	@Override
	public void run() {
		try {
			if (lockOrder == 1) {
				System.out.println(1);
				lock1.lockInterruptibly();
				System.out.println(2);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {

				}
				System.out.println(3);
				lock2.lockInterruptibly();
				System.out.println(4);
			} else {
				System.out.println(5);
				lock2.lockInterruptibly();
				System.out.println(6);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
				System.out.println(7);
				lock1.lockInterruptibly();
				System.out.println(8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(lock1.isHeldByCurrentThread()){
				System.out.println("unlock1");
				lock1.unlock();
			}
			if(lock2.isHeldByCurrentThread()){
				System.out.println("unlock2");
				lock2.unlock();
			}
			
			
			System.out.println(Thread.currentThread().getId() + "\t 退出");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new SimpleInterruptReentrantLock(1));
		Thread t2 = new Thread(new SimpleInterruptReentrantLock(2));
		
		t1.start();
		t2.start();
		
		Thread.sleep(150);
		
		t2.interrupt();
		System.out.println("out");
	}

}
