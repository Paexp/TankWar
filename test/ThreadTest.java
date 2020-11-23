import org.junit.jupiter.api.Test;

public class ThreadTest {
    private static void run() {
        System.out.println("Hello");
    }

    @Test
    public void testThread() {
        new TT().start();
        new Thread(new C()).start();

        // 匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("anonymous");
            }
        }).start();

        // java8 lambda expression
        // 当要传递的类只有一个方法需要重写时(Runnable->函数式接口)，可以省掉方法名
        new Thread(() -> {
            System.out.println("Hello");
        }).start();
    }
}

// 继承Thread类，重写run方法；不灵活
class TT extends Thread {
    @Override
    public void run() {
        System.out.println("TT");
    }
}

// 实现Runnable接口，实现run方法；灵活
class C implements Runnable {
    @Override
    public void run() {
        System.out.println("C");
    }
}
