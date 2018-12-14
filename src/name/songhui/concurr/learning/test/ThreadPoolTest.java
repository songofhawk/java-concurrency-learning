package name.songhui.concurr.learning.test;

import name.songhui.concurr.learning.bean.Person;
import name.songhui.concurr.learning.thread.Callee;
import org.junit.Test;

import java.util.concurrent.*;

public class ThreadPoolTest {

    @Test
    public void TestThreadPool(){
        ExecutorService pool = Executors.newFixedThreadPool(3);

        Callable<Person> worker1 = new Callee();
        Future ft1 = pool.submit(worker1);

        Callable<Person> worker2 = new Callee();
        Future ft2 = pool.submit(worker2);

        Callable<Person> worker3 = new Callee();
        Future ft3 = pool.submit(worker3);

        System.out.println("准备通知线程池shutdown...");
        pool.shutdown();
        System.out.println("已通知线程池shutdown");
        try {
            pool.awaitTermination(2L, TimeUnit.SECONDS);
            System.out.println("线程池完全结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
