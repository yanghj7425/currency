package chapter3;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 可以向 ForkJoinPool 线程池条提交 ForkJoinTask 任务: 支持 fork 分解 和 join 等待的任务。
 * ForkJoinTask 有2个子类，RecursiveAction（没有返回值的任务） 和 RecursiveTask（有返回值的任务）
 */
public class SimpleForkJoinCountTask extends RecursiveTask<Long> {
    private static final int THREAD_HOLD = 100;

    // 子任务数量
    private static final int SUB_TASK_COUNT = 100;
    private long start;
    private long end;


    public SimpleForkJoinCountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCompute = (end - start) < THREAD_HOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            long step = (start + end) / SUB_TASK_COUNT;
            ArrayList<SimpleForkJoinCountTask> subTasks = new ArrayList<>();
            long pos = start;
            // 划分任务数量
            for (int i = 0; i < SUB_TASK_COUNT; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                SimpleForkJoinCountTask subTask = new SimpleForkJoinCountTask(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (SimpleForkJoinCountTask task : subTasks) {
                sum += task.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        // 建立 ForkJoinPool 线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SimpleForkJoinCountTask forkJoinCountTask = new SimpleForkJoinCountTask(0, 100);
        ForkJoinTask<Long> result = forkJoinPool.submit(forkJoinCountTask);
        try {
            Long retInt = result.get();
            System.out.println(" Sum: " + retInt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
