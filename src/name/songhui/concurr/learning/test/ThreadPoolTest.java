package name.songhui.concurr.learning.test;

import name.songhui.concurr.learning.bean.Person;
import name.songhui.concurr.learning.thread.Callee;
import name.songhui.concurr.learning.util.ConcurUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {

    @Test
    public void TestThreadPool(){
        ExecutorService pool = Executors.newFixedThreadPool(3);

        Callable<Person> worker1 = new Callee(1);
        Future ft1 = pool.submit(worker1);

        Callable<Person> worker2 = new Callee(2);
        Future ft2 = pool.submit(worker2);

        Callable<Person> worker3 = new Callee(3);
        Future ft3 = pool.submit(worker3);

        ConcurUtil.shutdownPool(pool);
        System.out.println("Person1:"+ConcurUtil.getResultForFuture(ft1));
        System.out.println("Person2:"+ConcurUtil.getResultForFuture(ft2));
        System.out.println("Person3:"+ConcurUtil.getResultForFuture(ft3));
    }


    @Test
    public void TestThreadPoolExecutor(){
        /**
         * @param corePoolSize 线程池核心容量, 其实也可以看做是最小容量——无论有没有任务，永远会保持这个容量
         * @param maximumPoolSize 线程池最大容量，随着任务的增加，只要超过了核心容量，就会分配新的线程，直到达到了这个最大容量
         * @param keepAliveTime 保持活跃的等待时间，核心容量以外的线程，如果超过了这个等待时间，就会被回收，以减少资源占用
         * @param unit 前面等待时间参数的单位，Concurrency包定义了自己的时间单位
         * @param workQueue 等待队列，其元素必须是Runnable实例
         *
         */
        ExecutorService pool = new ThreadPoolExecutor(2, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>() );
        List<Future<Person>> futureList = new ArrayList<Future<Person>>();

        for (int i=0; i<10; i++){
            Callable<Person> worker = new Callee(i+1);
            Future ft = pool.submit(worker);
            futureList.add(ft);
        }


        ConcurUtil.shutdownPool(pool);

        for (int i=0; i<futureList.size(); i++){
            Future<Person> future = futureList.get(i);
            System.out.println("Person"+(i+1)+":"+ConcurUtil.getResultForFuture(future));
        }

    }


}
