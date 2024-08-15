package io.github.zxh111222.util;

import java.lang.reflect.Constructor;

/**
 * @author XinhaoZheng
 * @version 1.0
 * @description: TODO
 * @date 2024/6/14 20:42
 */
public class MyReflectionUtil {
    public static <T> T getInstance(String className) {
        T instance;
        try {
            Class<?> downloaderClass = Class.forName(className);
            Constructor<?> declaredConstructor = downloaderClass.getDeclaredConstructor();
            instance = (T) declaredConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return instance;
    }
}
