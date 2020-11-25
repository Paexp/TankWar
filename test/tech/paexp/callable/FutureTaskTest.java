package tech.paexp.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Long> ft = new FutureTask<>(new MyTask());
        new Thread(ft).start();
        Long l = ft.get();
        System.out.println(l);
    }
}
