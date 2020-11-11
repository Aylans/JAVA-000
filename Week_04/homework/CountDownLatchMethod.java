import java.util.concurrent.CountDownLatch;

/**
 * created by Aylan
 * on 2020/11/11 8:22 下午
 * CountDownLatchMethod 方式
 */
public class CountDownLatchMethod {

    private volatile Integer value = null;
    CountDownLatch latch;

    public Integer getValue() throws InterruptedException {
        latch.await();
        return value;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public void sum(int num){
        value = fibo(num);
        latch.countDown();
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);
        final CountDownLatchMethod method = new CountDownLatchMethod();
        method.setLatch(latch);
        Thread thread = new Thread(()->method.sum(40));
        thread.start();
        int result = method.getValue();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

    }
}
