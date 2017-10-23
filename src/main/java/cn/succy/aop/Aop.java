package cn.succy.aop;

import cn.succy.aop.annotation.EnhancedAnnotation;
import cn.succy.aop.proxy.AbstractAspectProxy;
import cn.succy.aop.annotation.Aspect;
import cn.succy.aop.annotation.PointCut;
import cn.succy.aop.proxy.Proxy;
import cn.succy.aop.proxy.ProxyManager;
import cn.succy.aop.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Succy
 * @date 2017-10-02 10:38
 * @since 1.0.0
 **/

public class Aop {
    private static final Logger logger = LoggerFactory.getLogger(Aop.class);
    private static Map<Class<? extends AbstractAspectProxy>, AbstractAspectProxy> SINGLETON_ASPECT_OBJ_MAP
            = new ConcurrentHashMap<Class<? extends AbstractAspectProxy>, AbstractAspectProxy>();

    /**
     * 根据指定目标类获取里边标记的所有切面类对象
     *
     * @param targetClass 目标类对象
     * @return 切面类集合
     */
    private static Set<Class<? extends AbstractAspectProxy>> getAspectClassSetByTarget(Class<?> targetClass) {
        if (targetClass == null) {
            logger.error("get aspect class set failure, because target class is null");
            throw new RuntimeException("get aspect class set failure, because target class is null");
        }
        // 使用LinkedHashSet进行去重并保证顺序
        Set<Class<? extends AbstractAspectProxy>> aspectClassSet = new LinkedHashSet<Class<? extends AbstractAspectProxy>>();

        // 获取目标类上的@Aspect注解的值
        EnhancedAnnotation target = new EnhancedAnnotation(targetClass);
        if (target.isAnnotationPresent(Aspect.class)) {
            Aspect aspectAnnotation = target.getAnnotation(Aspect.class);
            Class<? extends AbstractAspectProxy>[] aspectClassArr = aspectAnnotation.value();
            Collections.addAll(aspectClassSet, aspectClassArr);
        }

        // 获取目标类中方法标记有@PointCut注解的值
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            EnhancedAnnotation methodAnnotation = new EnhancedAnnotation(method);
            if (methodAnnotation.isAnnotationPresent(PointCut.class)) {
                PointCut pointCut = methodAnnotation.getAnnotation(PointCut.class);
                Class<? extends AbstractAspectProxy>[] pointCutClassArr = pointCut.value();
                Collections.addAll(aspectClassSet, pointCutClassArr);
            }
        }
        return aspectClassSet;
    }

    /**
     * 使用该方法，对目标类进行增强，使其拥有AOP的特殊能力
     * @param clazz 目标类对象
     * @return 目标类对象的代理类实例
     */
    public static <T> T enhance(Class<T> clazz) {
        Set<Class<? extends AbstractAspectProxy>> aspectClassSet = getAspectClassSetByTarget(clazz);
        List<Proxy> proxyList = new ArrayList<Proxy>();
        for (Class<? extends AbstractAspectProxy> cls : aspectClassSet) {
            if (!SINGLETON_ASPECT_OBJ_MAP.containsKey(cls)) {
                SINGLETON_ASPECT_OBJ_MAP.put(cls, ReflectionUtil.newInstance(cls));
            }
            AbstractAspectProxy aspectProxy = SINGLETON_ASPECT_OBJ_MAP.get(cls);
            proxyList.add(aspectProxy);
        }
        return ProxyManager.createProxy(clazz, proxyList);
    }
}
