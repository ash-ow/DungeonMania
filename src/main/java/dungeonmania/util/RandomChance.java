package dungeonmania.util;

import java.util.Random;

public class RandomChance {
    static Random rand = new Random();

    public static boolean getRandomBoolean(float p){
        return rand.nextFloat() < p;
    }

    public static boolean getRandomBoolean(float p, int seed){
        rand = new Random(seed);
        return rand.nextFloat() < p;
    }
}
