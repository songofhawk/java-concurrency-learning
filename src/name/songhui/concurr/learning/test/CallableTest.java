package name.songhui.concurr.learning.test;

import name.songhui.concurr.learning.bean.Person;
import name.songhui.concurr.learning.thread.Callee;
import name.songhui.concurr.learning.thread.CalleeWithExp;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {


    /**
     * 主线程可以通过循环一直检查FutureTask的状态, 等状态变更以后,再获取返回结果
     */
    @Test
    public void TestLoopWaitFuture() {
        Callee callee1 = new Callee();
        FutureTask<Person> ft= new FutureTask<Person>(callee1);
        new Thread(ft).start();

        while(!ft.isDone()){
            try {
                System.out.println("检查线程执行完了吗...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Person result = getResultForFuture(ft);

        System.out.println("main thread get result: "+result.toString());
    }

    /**
     * 主线程也可以join子线程, 等子线程结束以后,再获取返回结果(这样似乎效率更高?)
     */
    @Test
    public void TestJoinWaitFuture() {
        Callee callee1 = new Callee();
        FutureTask<Person> ft= new FutureTask<Person>(callee1);
        Thread thread = new Thread(ft);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("ft.isDone: "+ft.isDone());

        Person result = getResultForFuture(ft);
        System.out.println("main thread get result: "+result.toString());
    }

    /**
     * 主线程可以捕获来自子线程的受检查异常
     */
    @Test
    public void TestInterruptException() {
        Callee callee1 = new Callee();
        FutureTask<Person> ft= new FutureTask<Person>(callee1);
        Thread thread = new Thread(ft);
        try {
            thread.start();

            thread.interrupt();

            System.out.println("ft.isDone: "+ft.isDone());

        } catch (Exception e){
            System.out.println("捕获了来自子线程的异常: "+e.getMessage());
        }
    }

    /**
     * 主线程无法捕获来自子线程的运行时异常
     */
    @Test
    public void TestRuntimeException() {
        CalleeWithExp callee1 = new CalleeWithExp();
        FutureTask<Person> ft= new FutureTask<Person>(callee1);
        Thread thread = new Thread(ft);
        try {
            thread.start();

            thread.join();

            System.out.println("ft.isDone: "+ft.isDone());

        } catch (Exception e){
            System.out.println("捕获了来自子线程的异常: "+e.getMessage());
        }
    }

    /**
     * 子线程的运行时异常对主线程毫无影响,甚至没有引起任何影响,貌似是被框架"吃掉"了
     * (从源码来看,貌似是FutureTask的run方法里吃掉的)
     */
    @Test
    public void TestRuntimeExceptionWithoutCatch() {
        CalleeWithExp callee1 = new CalleeWithExp();
        FutureTask<Person> ft= new FutureTask<Person>(callee1);
        Thread thread = new Thread(ft);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("ft.isDone: "+ft.isDone());

    }



    private Person getResultForFuture(FutureTask<Person> ft) {
        try {
            return ft.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


}
