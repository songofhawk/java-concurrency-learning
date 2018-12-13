package name.songhui.concurr.learning.thread;

import name.songhui.concurr.learning.exception.MyException;

import java.util.concurrent.atomic.AtomicInteger;

public class RunnerWithExp implements Runnable{
    AtomicInteger seq = new AtomicInteger(0);
    public void run() throws MyException {
        throw new MyException("我出错啦:"+seq.incrementAndGet());
    }

}

