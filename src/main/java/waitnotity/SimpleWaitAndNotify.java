package waitnotity;

public class SimpleWaitAndNotify {
	final static Object object = new Object();

	public static class T1 extends Thread {
		@Override
		public void run() {
			synchronized (object) {
				System.out.println(System.currentTimeMillis() + "\t" + "T1 : start");
				try {
					System.out.println(System.currentTimeMillis() + "\t + T1 wait");
					object.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() + "\t T1 end");
			}
		}
	}

	public static class T2 extends Thread {

		@Override
		public void run() {

			synchronized (object) {
				System.out.println(System.currentTimeMillis() + "\t" + "T2 : start");
				try {
					System.out.println(System.currentTimeMillis() + "\t + T2 wait");
					object.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() + "\t T2 end");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new T1();
		Thread t2 = new T2();
		t1.start();
		t2.start();

		Thread.sleep(100);
		synchronized (object) {
			System.out.println("eee");
			object.notify();
		}

	}

}
