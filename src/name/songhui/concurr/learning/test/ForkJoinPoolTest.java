package name.songhui.concurr.learning.test;

/**
 * Project Name:Spring0725
 * File Name:ForkJoinPoolAction.java
 * Package Name:work1201.basic
 * Date:2017年12月4日下午2:26:55
 * Copyright (c) 2017, 深圳金融电子结算中心 All Rights Reserved.
 */

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * ClassName:ForkJoinPoolAction <br/>
 * Function: 使用ForkJoinPool完成一个任务的分段执行
 * 简单的打印0-300的数值。用多线程实现并行执行
 * Date:     2017年12月4日 下午2:26:55 <br/>
 * @author prd-lxw
 * @version 1.0
 * @since JDK 1.7
 * @see
 */
public class ForkJoinPoolTest {

    @Test
    public void testAction() throws Exception {
        PrintTask task = new PrintTask(0, 300);
        //创建实例，并执行分割任务
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);
        //线程阻塞，等待所有任务完成
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
    }

    /**
     * ClassName:ForJoinPollTask <br/>
     * Function: 对一个长度为100的元素值进行累加
     * Date:     2017年12月4日 下午5:41:46 <br/>
     * @author   prd-lxw
     * @version   1.0
     * @since    JDK 1.7
     * @see
     */
    @Test
    public void testTask() throws ExecutionException, InterruptedException {
        int[] arr = new int[500000000];
        Random random = new Random();
        int total =0;
        //初始化100个数组元素
        long time=0,term=0;

        //循环版
        time = System.currentTimeMillis();
        for(int i=0,len = arr.length;i<len;i++){
            int temp = random.nextInt(20);
            //对数组元素赋值，并将数组元素的值添加到sum总和中
            total += (arr[i]=temp);
        }
        System.out.println("初始化数组总和："+total);
        term = System.currentTimeMillis() - time;
        System.out.println("用时："+term);

        //并发版
        time = System.currentTimeMillis();
        SumTask task = new SumTask(arr, 0, arr.length);
//        创建一个通用池，这个是jdk1.8提供的功能
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Future<Integer> future = pool.submit(task); //提交分解的SumTask 任务
        System.out.println("多线程执行结果："+future.get());
        pool.shutdown(); //关闭线程池
        term = System.currentTimeMillis() - time;
        System.out.println("用时："+term);


    }

}

/**
 * ClassName: PrintTask <br/>
 * Function: 继承RecursiveAction来实现“可分解”的任务。
 * date: 2017年12月4日 下午5:17:41 <br/>
 *
 * @author prd-lxw
 * @version 1.0
 * @since JDK 1.7
 */
class PrintTask extends RecursiveAction {
    private static final int THRESHOLD = 50; //最多只能打印50个数
    private int start;
    private int end;


    public PrintTask(int start, int end) {
        super();
        this.start = start;
        this.end = end;
    }


    @Override
    protected void compute() {

        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "的i值：" + i);
            }
        } else {
            int middle = (start + end) / 2;
            PrintTask left = new PrintTask(start, middle);
            PrintTask right = new PrintTask(middle, end);
            //并行执行两个“小任务”
            left.fork();
            right.fork();
        }

    }

}


/**
 * ClassName: SumTask <br/>
 * Function: 继承抽象类RecursiveTask，通过返回的结果，来实现数组的多线程分段累累加
 *  RecursiveTask 具有返回值
 * date: 2017年12月4日 下午6:08:11 <br/>
 *
 * @author prd-lxw
 * @version 1.0
 * @since JDK 1.7
 */
class SumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 20; //每个小任务 最多只累加20个数
    private int arry[];
    private int start;
    private int end;



    /**
     * Creates a new instance of SumTask.
     * 累加从start到end的arry数组
     * @param arry
     * @param start
     * @param end
     */
    public SumTask(int[] arry, int start, int end) {
        super();
        this.arry = arry;
        this.start = start;
        this.end = end;
    }



    @Override
    protected Integer compute() {
        int sum =0;
        //当end与start之间的差小于threshold时，开始进行实际的累加
        if(end - start <THRESHOLD){
            for(int i= start;i<end;i++){
                sum += arry[i];
            }
            return sum;
        }else {//当end与start之间的差大于threshold，即要累加的数超过20个时候，将大任务分解成小任务
            int middle = (start+ end)/2;
            SumTask left = new SumTask(arry, start, middle);
            SumTask right = new SumTask(arry, middle, end);
            //并行执行两个 小任务
            left.fork();
            right.fork();
            //把两个小任务累加的结果合并起来
            return left.join()+right.join();
        }

    }

}