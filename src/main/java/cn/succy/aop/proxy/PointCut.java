package cn.succy.aop.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于在目标类中，标注方法作为切入点
 * @author Succy
 * @since 1.0.0
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PointCut {
    Class<? extends AbstractAspectProxy> value()[];
}
