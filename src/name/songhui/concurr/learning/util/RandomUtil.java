package name.songhui.concurr.learning.util;

import java.util.Random;

public class RandomUtil {
    public static int random(int min, int max){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

}
