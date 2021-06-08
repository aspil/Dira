package di.uoa.gr.dira.utils;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectGenerator {
    public static <T> T generateObject(Class<T> clazz) {
        return new EasyRandom().nextObject(clazz);
    }

    public static <T> T generateObject(Class<T> clazz, EasyRandomParameters params) {
        return new EasyRandom(params).nextObject(clazz);
    }

    public static <T> List<T> generateObjectList(Class<T> clazz, int numberOfObjects) {
        return new EasyRandom().objects(clazz, numberOfObjects).collect(Collectors.toList());
    }

    public static <T> List<T> generateObjectList(Class<T> clazz, EasyRandomParameters params, int numberOfObjects) {
        List<T> res = new ArrayList<>(numberOfObjects);

        for (int i = 0; i != numberOfObjects; ++i) {
            res.add(generateObject(clazz, params));
        }

        return res;
    }
}
