import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * created by Aylan
 * on 2020/11/11 7:59 下午
 * CyclicBarrierMethod 方式
 */
public class CyclicBarrierMethod {

    private volatile Integer value = null;
    CyclicBarrier barrier;

    public Integer getValue() {
        return value;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.barrier = cyclicBarrier;
    }

    public void sum(int num) throws BrokenBarrierException, InterruptedException {
        value = fibo(num);
        barrier.await();
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final CyclicBarrierMethod method = new CyclicBarrierMethod();
        CyclicBarrier barrier = new CyclicBarrier(1,()->{
            int result = method.getValue();//这里得到返回值
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        });
        method.setCyclicBarrier(barrier);
        Thread thread = new Thread(()->{
            try {
                method.sum(40);
            }catch (BrokenBarrierException | InterruptedException e){
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
