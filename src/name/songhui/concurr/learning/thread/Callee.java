package name.songhui.concurr.learning.thread;

import name.songhui.concurr.learning.bean.Person;
import name.songhui.concurr.learning.util.RandomUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Callee implements Callable {
    AtomicInteger seq = new AtomicInteger(0);

    /**
     * call接口可以抛出受检查的异常
     * @return
     * @throws InterruptedException
     */
    @Override
    public Person call() throws InterruptedException {
        Person p = new Person("person"+seq.incrementAndGet(), RandomUtil.random(0,150));
        System.out.println("In thread: "+p.toString());
        Thread.sleep(1000);
        return  p;
    }
}


