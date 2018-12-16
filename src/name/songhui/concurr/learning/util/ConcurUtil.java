package name.songhui.concurr.learning.util;

import name.songhui.concurr.learning.bean.Person;

import java.util.concurrent.*;

public class ConcurUtil {

    public static <T> T getResultForFuture(FutureTask<T> ft) {
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

    public static <T> T getResultForFuture(Future<T> ft) {
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

    public static void shutdownPool(ExecutorService pool) {
        System.out.println("准备通知线程池shutdown...");
        //shutdown只是不能再提交新的任务,已经进入队列和正在进行的任务不受影响. 调用之后商城处于SHUTDOWN状态(这也就意味着这个状态是个中间状态,它最终会过渡到TERMINATED)
        //如果想立即关闭线程池,线程池停止接受新的任务，同时线程池取消所有执行的任务和已经进入队列但是还没有执行的任务，这时候线程池处于STOP状态((STOP也是个中间状态,它最终会过渡到TERMINATED)
        pool.shutdown();
        System.out.println("已通知线程池shutdown");
        try {
            //在一定时间内,等在线程池中的任务全部结束
            if(pool.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println("线程池完全结束");
            }else{
                System.out.println("线程池等待超时....");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
