package cn.succy.aop.annotation;

import cn.succy.aop.proxy.AbstractAspectProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注在目标类上，表明该类所有方法都要被参数中的所有切面拦截
 * @author Succy
 * @since 1.0.0
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends AbstractAspectProxy>[] value();
}
