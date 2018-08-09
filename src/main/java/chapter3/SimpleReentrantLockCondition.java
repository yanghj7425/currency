package chapter3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁的条件（Condition）
 */
public class SimpleReentrantLockCondition implements Runnable {
	
	public static ReentrantLock lock = new ReentrantLock();
	public static Condition condition  = lock.newCondition();
	
	@Override
	public void run() {
		try{
			/**
			 * 获取锁
			 */
			lock.lock();
			// 等待条件，等待时释放资源;
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
		/**
		 * 唤醒等待条件，唤醒后需要获取相应资源的锁
		 */
		condition.signal();
		lock.unlock();
	}

}
