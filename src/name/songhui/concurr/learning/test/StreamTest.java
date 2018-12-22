package name.songhui.concurr.learning.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void testFilter() {
        IntStream stream = IntStream.range(0, 1000);
        int[] array = stream.parallel().filter(item -> item % 10 == 0).toArray();
        System.out.println(Arrays.toString(array));
    }


    @Test
    public void testMap() {
        IntStream stream = IntStream.range(0, 1000);
        int[] array = stream.parallel().filter(item -> item % 10 == 0).map(item -> item + 5).toArray();
        System.out.println(Arrays.toString(array));
    }


    @Test
    public void TestFlatMap() {
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
        outputStream.forEach(item -> System.out.println(item));
    }

    @Test
    public void testCollect() {
        Stream<String> stream = Stream.of("a", "b", "c");
//        Stream<String> stream = Arrays.stream( new String[] {"a", "b", "c"});
        String str = stream.collect(Collectors.joining(","));
        System.out.println(str);
    }


    @Test
    public void testReduceSimple() {
        Stream<String> stream = Stream.of("a", "b", "c");
        String str = stream.reduce((a, b) -> a + "," + b).get();
        System.out.println(str);
    }

    @Test
    public void testReduce() {
        Stream<String> stream = Stream.of("a", "b", "c");
        String str = stream.reduce(new StringBuilder(), (sb, s) -> sb.append(s).append(','), StringBuilder::append).toString();
        System.out.println(str);
    }

    @Test
    public void testParallelReduce() {
        Stream<String> stream = Stream.of("a", "b", "c");
        //这里不知道为什么,并行以后,拼接的字符串会远远超过3个字母——貌似是sb这个变量被多线程共享了，又没有加锁，所以stream的并发也不见得是安全的
        String str = stream.parallel().reduce(new StringBuilder(), (sb, s) -> sb.append(s).append(','), (sb, s) -> sb.append(s).append(";  ")).toString();
        System.out.println(str);
    }

    @Test
    public void testParallelReduceSimple() {
        Stream<String> stream = Stream.of("a", "b", "c");
        String str = stream.parallel().reduce((a, b) -> a + "," + b).get();
        System.out.println(str);
    }

}
