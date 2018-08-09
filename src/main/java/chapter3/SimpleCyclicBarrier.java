package chapter3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅，凑够一定数量的线程后执行指定的动作（实现 Runnable 接口）
 */
public class SimpleCyclicBarrier {

	public static class Soldier implements Runnable {
		private String solider;
		private CyclicBarrier cyclic;

		public Soldier(String solider, CyclicBarrier cyclic) {
			this.solider = solider;
			this.cyclic = cyclic;
		}

		@Override
		public void run() {
			try {
				cyclic.await();
				doWork();
				cyclic.await();
				doWord1();
				cyclic.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		private void doWord1() throws InterruptedException{
			Thread.sleep(1000);
			System.out.println(solider + "\t 2任务完成");
		}
		private void doWork() throws InterruptedException {
			Thread.sleep(1000);
			System.out.println(solider + "\t 任务完成");
		}

	}

	public static class BarrierRun implements Runnable {
		boolean flag;
		int number;
		int around = 0;

		public BarrierRun(boolean flag, int number) {
			this.flag = flag;
			this.number = number;
		}

		@Override
		public void run() {
			if (flag) {
				System.out.println(number + "\t 任务完成");
				flag = false;
				around++;
			} else {
				System.out.println(number + "\t 集合完毕");
				flag = true;
			}
		}
	}

	public static void main(String[] args) {
		final int N = 10;
		Thread allSolider[] = new Thread[N];
		BarrierRun barrienRun = new BarrierRun(false, N);
		int around = barrienRun.around;

		CyclicBarrier cyclic = new CyclicBarrier(N, barrienRun);
		System.out.println("collection~~");
		for (int i = 0; i < N; i++) {
			System.out.println("solider\t" + i + "\t coming.");
			allSolider[i] = new Thread(new Soldier("solider" + i, cyclic));
			allSolider[i].start();
		}

	}

}
