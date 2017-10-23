package cn.succy.aop.test;

import cn.succy.aop.annotation.EnhancedAnnotation;
import cn.succy.aop.test.example.DemoService;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Succy
 * @date 2017-10-21 19:01
 **/

public class AnnotationTest {
    @Test
    @SuppressWarnings("unchecked")
    public void test() throws Exception{
        Class clazz = DemoService.class;
        Method method = clazz.getDeclaredMethod("login", String.class, String.class);
        EnhancedAnnotation metaAnnotaion = new EnhancedAnnotation(clazz);
        Annotation[] annotations = metaAnnotaion.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }
}
