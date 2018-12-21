package name.songhui.concurr.learning.test;

import name.songhui.concurr.learning.thread.Runner;
import name.songhui.concurr.learning.thread.RunnerWithExp;
import org.junit.Test;

public class RunnableTest {

    /**
     * JUnit的测试方法, 尽管不是守护线程, 它结束以后,整个进程就结束了,不会等待子线程完成
     */
    @Test
    public void TestRun() {
        System.out.println("main thread is daemon:"+Thread.currentThread().isDaemon());
        Runner runner1 = new Runner();
        Thread thread = new Thread(runner1);
        thread.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread end!");
    }

    /**
     * main方法结束以后, 整个进程不会结束, 而是等待子线程完成以后才结束
     * @param args
     */
    public static void main(String[] args) {
        Runner runner1 = new Runner();
        Thread thread = new Thread(runner1);
        System.out.println("main thread is daemon:"+thread.isDaemon());
        thread.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread end!");
    }

    @Test
    public void TestWaitSubThread() {
        Runner runner1 = new Runner();
        Thread thread = new Thread(runner1);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("main thread end!");
    }

    /**
     *  子线程的异常会中断主线程执行
     */
    @Test
    public  void TestExceptionInSubThread(){
        RunnerWithExp runner1 = new RunnerWithExp();

        new Thread(runner1).start();
        System.out.println("main thread end!");
    }

    /**
     * 主线程是无法捕获子线程异常的,只能由子线程自己捕获,所以子线程应当保证自己不要抛异常
     */
    @Test
    public  void TestCatchForSubThread(){
        RunnerWithExp runner1 = new RunnerWithExp();
        try {
            new Thread(runner1).start();
        }catch (Exception e){
            System.out.println("发现子线程出错:"+e.getMessage());
        }
        System.out.println("main thread end!");
    }


}
