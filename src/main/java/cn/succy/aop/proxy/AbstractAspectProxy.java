package cn.succy.aop.proxy;

import cn.succy.aop.annotation.Aspect;
import cn.succy.aop.annotation.EnhancedAnnotation;
import cn.succy.aop.annotation.PointCut;
import cn.succy.aop.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 抽象切面代理类
 *
 * @author Succy
 * @date 2017-09-30 10:35
 * @since 1.0.0
 **/

public abstract class AbstractAspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractAspectProxy.class);

    /**
     * @param proxyChain 代理链
     * @return
     * @throws Throwable
     */
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        Object target = proxyChain.getTargetObj();
        JoinPoint joinPoint = new JoinPoint(targetClass, targetMethod, params, target, this);

        // 对目标类对象和目标方法的注解进行增强，以达到使用组合注解的目的
        EnhancedAnnotation targetClsAnnotated = new EnhancedAnnotation(targetClass);
        EnhancedAnnotation methodAnnotated = new EnhancedAnnotation(targetMethod);

        Object result;
        if (isIntercept(joinPoint)) {
            boolean isInvoke = (targetClsAnnotated.isAnnotationPresent(Aspect.class)
                    && ArrayUtil.contains(targetClsAnnotated.getAnnotation(Aspect.class).value(), this.getClass()))
                    || ((methodAnnotated.isAnnotationPresent(PointCut.class)
                    && ArrayUtil.contains(methodAnnotated.getAnnotation(PointCut.class).value(), this.getClass())));
            if (isInvoke) {
                result = around(proxyChain);
            } else {
                result = proxyChain.doProxyChain();
            }
        } else {
            result = proxyChain.doProxyChain();
        }

        return result;
    }

    /**
     * 环绕通知/增强 如果子类中重写了该方法，则重写的before() after()等方法将不起作用。
     *
     * @param chain 代理链
     * @return
     * @throws Throwable
     */
    protected Object around(ProxyChain chain) throws Throwable {
        Class<?> targetClass = chain.getTargetClass();
        Method targetMethod = chain.getTargetMethod();
        Object[] params = chain.getMethodParams();
        Object target = chain.getTargetObj();
        JoinPoint joinPoint = new JoinPoint(targetClass, targetMethod, params, target, this);

        Object result = null;
        try {
            before(joinPoint);
            result = chain.doProxyChain();
            after(joinPoint, result);
        } catch (Exception e) {
            logger.error("invoke proxy occur error", e);
            throwable(joinPoint, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }

    /**
     * 前置通知/增强 切面类只需按照需求重写该方法即可做的前置通知
     *
     * @param joinPoint 连接点
     * @throws Throwable
     */
    protected void before(JoinPoint joinPoint) throws Throwable {

    }

    /**
     * 后置通知/增强 切面类只需按照需求重写该方法即可做的后置通知
     *
     * @param joinPoint 连接点
     * @throws Throwable
     */
    protected void after(JoinPoint joinPoint, Object result) throws Throwable {

    }

    /**
     * 是否拦截,如果在项目中，不想要通过删掉配置好的注解起到去除切面拦截的作用，可以通过重写该方法
     *
     * @param joinPoint 连接点
     * @return 是否拦截
     * @throws Throwable
     */
    protected boolean isIntercept(JoinPoint joinPoint) throws Throwable {
        return true;
    }

    /**
     * 异常通知
     *
     * @param joinPoint 连接点
     * @param e         异常
     */
    protected void throwable(JoinPoint joinPoint, Throwable e) {
    }

    /**
     * 结束方法，如果重写该方法，不管目标方法执行过程中是否出现异常，该方法都执行
     */
    protected void end() {
    }

}
