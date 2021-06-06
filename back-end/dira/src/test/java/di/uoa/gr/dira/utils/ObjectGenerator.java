package di.uoa.gr.dira.utils;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class ObjectGenerator {
    public static <T> T generateObject(Class<T> clazz) {
        return new EasyRandom().nextObject(clazz);
    }

    public static <T> T generateObject(Class<T> clazz, EasyRandomParameters params) {
        return new EasyRandom(params).nextObject(clazz);
    }
}
