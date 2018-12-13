package name.songhui.concurr.learning.thread;

import name.songhui.concurr.learning.exception.MyException;
import name.songhui.concurr.learning.bean.Person;
import name.songhui.concurr.learning.util.RandomUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class CalleeWithExp implements Callable {
    AtomicInteger seq = new AtomicInteger(0);
    public Person call() throws MyException {
        Person p = new Person("person"+seq.incrementAndGet(), RandomUtil.random(0,150));
        System.out.println("In thread: "+p.toString());
        throw new MyException("我出错啦:"+seq.get());
    }

}

