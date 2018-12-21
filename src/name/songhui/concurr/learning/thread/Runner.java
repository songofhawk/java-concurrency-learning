package name.songhui.concurr.learning.thread;

import name.songhui.concurr.learning.bean.Person;
import name.songhui.concurr.learning.util.RandomUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class Runner implements Runnable {
    AtomicInteger seq = new AtomicInteger(0);

    ThreadLocal<Integer> localInt = new ThreadLocal<>();

    /**
     * run方法不能抛出受检查的异常,必须用try catch块包围
     */
    @Override
    public void run() {
        System.out.println("thread start");
        System.out.println("thread is daemon:"+Thread.currentThread().isDaemon());

        Person p = new Person("person"+seq.incrementAndGet(), RandomUtil.random(0,150));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("In thread: "+p.toString());
    }

}

