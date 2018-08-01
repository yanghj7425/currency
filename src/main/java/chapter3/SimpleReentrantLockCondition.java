package chapter3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleReentrantLockCondition implements Runnable {
	
	public static ReentrantLock lock = new ReentrantLock();
	public static Condition condition  = lock.newCondition();
	
	@Override
	public void run() {
		try{
			lock.lock();
			condition.await();
			System.out.println("Thread is going on");
			
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new SimpleReentrantLockCondition());
		t1.start();
		Thread.sleep(1000);
		lock.lock();
		condition.signal();
		lock.unlock();
	}

}
