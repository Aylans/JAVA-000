import java.util.concurrent.CompletableFuture;

/**
 * created by Aylan
 * on 2020/11/11 8:19 下午
 * CompletableFutureMethod 方式
 */
public class CompletableFutureMethod {

    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        int result = CompletableFuture.supplyAsync(CompletableFutureMethod::sum).join();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
