package interrupt;

public class ThreadInterrupt {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread() {
			boolean isStop = false;

			@Override
			public void run() {
				while (!isStop) {
					Thread.yield();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					System.out.println("thread t1");
					if (Thread.currentThread().isInterrupted()) {
						isStop = true;
						System.out.println("stop");
					}
				}

			}

		};

		t1.start();
		Thread.sleep(200);
		t1.interrupt();
	}

}
