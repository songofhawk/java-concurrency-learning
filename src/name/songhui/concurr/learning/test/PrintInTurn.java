package name.songhui.concurr.learning.test;

public class PrintInTurn implements Runnable {
    /**
     * 逐级获取释放锁,形成的多线程按顺序打印
     * @author DreamSea
     * @time 2015.3.9
     * 来自林炳文的博文:https://blog.csdn.net/Evankaka/article/details/44153709
    */

        private String name;
        private Object prev;
        private Object self;

        private PrintInTurn(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 10;
            while (count > 0) {
                synchronized (prev) {
                    synchronized (self) {
                        System.out.print(name);
                        count--;

                        self.notify();
                    }
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        public static void main(String[] args) throws Exception {
            Object a = new Object();
            Object b = new Object();
            Object c = new Object();
            PrintInTurn pa = new PrintInTurn("A", c, a);
            PrintInTurn pb = new PrintInTurn("B", a, b);
            PrintInTurn pc = new PrintInTurn("C", b, c);


            new Thread(pa).start();
            Thread.sleep(100);  //确保按顺序A、B、C执行
            new Thread(pb).start();
            Thread.sleep(100);
            new Thread(pc).start();
            Thread.sleep(100);
        }


}
