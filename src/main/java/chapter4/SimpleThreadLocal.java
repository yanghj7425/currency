package chapter4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadLocal {
    // SimpleDateFormat.parse 不支持多线程
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 使用 ThreadLocal 为每一个线程都产生一个 SimpleDateLocal
     */
    private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<>();

    public static class ParseDate implements Runnable {
        int i;

        public ParseDate(int i) {
            this.i = i;
        }


        @Override
        public void run() {
            try {
                if (tl.get() == null) {
                    tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }

                Date t = tl.get().parse("2018-08-18 23:78:43");
                System.out.println(t);
                /**
                 * 会抛出异常
                 */
//                String timeStr = sdf.format(new Date());
//                Date t = sdf.parse(timeStr);
//                System.out.println(i + ": " + t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 40; i++) {
            executorService.execute(new ParseDate(i));
        }
        executorService.shutdown();
    }


}
