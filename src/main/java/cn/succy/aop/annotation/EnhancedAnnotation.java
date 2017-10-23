package cn.succy.aop.annotation;

import sun.reflect.annotation.AnnotationParser;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 增强的注解
 * 对JDK的原生注解机制做一个增强，支持类似Spring的组合注解.
 * 里边核心实现使用了递归，另外List#contains底层也是通过循环实现
 * 理论上这里是比较耗性能的，但是由于目前没有比较好的算法去实现，
 * 只能将就使用。后面一旦发现更好实现，会对其进行重构。
 *
 * @author Succy
 * @date 2017-10-22 21:49
 **/

public class EnhancedAnnotation implements AnnotatedElement {
    private static final List<Class<? extends Annotation>> META_ANNOTATIONS = new ArrayList<Class<? extends Annotation>>();
    private final Map<Class<? extends Annotation>, Annotation> annotationMap = new HashMap<Class<? extends Annotation>, Annotation>(16);

    static {
        META_ANNOTATIONS.add(Target.class);
        META_ANNOTATIONS.add(Retention.class);
        META_ANNOTATIONS.add(Inherited.class);
        META_ANNOTATIONS.add(Documented.class);
        META_ANNOTATIONS.add(SuppressWarnings.class);
        META_ANNOTATIONS.add(Override.class);
        META_ANNOTATIONS.add(Deprecated.class);
    }

    public EnhancedAnnotation(AnnotatedElement element) {
        parse(element);
    }

    /**
     * 进行递归解析注解，直到全部都是元注解为止
     *
     * @param element Class, Method, Field等
     */
    private void parse(AnnotatedElement element) {
        for (Annotation annotation : element.getDeclaredAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (!META_ANNOTATIONS.contains(annotationType)) {
                annotationMap.put(annotationType, annotation);
                parse(annotationType);
            }
        }
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return annotationMap.containsKey(annotationClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        Annotation annotation = annotationMap.get(annotationClass);
        return annotation == null ? null : (T) annotation;
    }

    @Override
    public Annotation[] getAnnotations() {
        return AnnotationParser.toArray(annotationMap);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return AnnotationParser.toArray(annotationMap);
    }
}
