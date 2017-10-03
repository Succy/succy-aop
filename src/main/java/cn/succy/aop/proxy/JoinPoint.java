package cn.succy.aop.proxy;

import java.lang.reflect.Method;

/**
 * 连接点,封装了切点的一些信息,方便用户获取
 * 注：参考Spring的JoinPoint对象
 *
 * @author Succy
 * @date 2017-09-30 10:46
 * @since 1.0.0
 **/

public class JoinPoint {
    private final Class<?> targetClass;
    private final Method targetMethod;
    private final Object[] args;
    private final Object target;
    private final Object thiz;

    public JoinPoint(Class<?> targetClass, Method targetMethod, Object[] args, Object target, Object thiz) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.args = args;
        this.target = target;
        this.thiz = thiz;
    }

    /**
     * 获取被代理的目标类对象
     *
     * @return 被代理的目标类对象
     */
    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * 获取被代理的目标方法
     *
     * @return 被代理的目标方法
     */
    public Method getTargetMethod() {
        return targetMethod;
    }

    /**
     * 获取目标方法的参数
     *
     * @return 目标方法的参数
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * 获取被代理的目标对象
     *
     * @return 被代理的目标对象
     */
    public Object getTarget() {
        return target;
    }

    /**
     * 获取代理类对象
     *
     * @return 代理类对象
     */
    public Object getProxy() {
        return thiz;
    }
}
