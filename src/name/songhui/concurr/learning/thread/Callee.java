package name.songhui.concurr.learning.thread;

import name.songhui.concurr.learning.bean.Person;
import name.songhui.concurr.learning.util.RandomUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Callee implements Callable {
    AtomicInteger counter = new AtomicInteger(0);

    private Integer seq=null;

    public Callee()
    {
        super();
    }

    public  Callee(int seq)
    {
        this.seq = seq;
    }

    /**
     * call接口可以抛出受检查的异常EE
     * @return
     * @throws InterruptedException
     */
    @Override
    public Person call() throws InterruptedException {
        Person p = new Person("person"+ counter.incrementAndGet(), RandomUtil.random(0,150));
        System.out.println("In thread("+seq+"), create a Person: "+p.toString());
        Thread.sleep(1000);
        return  p;
    }


}


