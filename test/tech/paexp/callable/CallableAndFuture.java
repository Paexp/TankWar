package tech.paexp.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Long> future = service.submit(new MyTask());

        // 阻塞的 wait until there is a result --- blocked
        long l = future.get();
        System.out.println(l);

        System.out.println("go on!");
    }
}
