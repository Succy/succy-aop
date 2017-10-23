package cn.succy.aop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 *
 * @author Succy
 * @date 2017-09-30 11:00
 * @since 1.0.0
 **/

public final class ReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    private ReflectionUtil() {
        throw new AssertionError();
    }

    /**
     * 利用反射创建实例对象
     *
     * @param clazz 待创建实例的类对象
     * @return 实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz) {
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            logger.error("new instance error", e);
            throw new RuntimeException(e);
        }
        return (T) instance;
    }

    //public static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
    //    if (clazz.isAnnotationPresent(annotation)) {
    //        return true;
    //    }
    //    Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
    //}

}
