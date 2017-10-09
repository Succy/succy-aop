package cn.succy.aop.test.example;

import cn.succy.aop.proxy.AbstractAspectProxy;
import cn.succy.aop.proxy.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志切面
 *
 * @author Succy
 * @date 2017-10-09 16:27
 **/

public class LogAspect extends AbstractAspectProxy {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Override
    protected void before(JoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getTargetMethod().getName();
        logger.info("{} 方法被要调用了", methodName);
    }

    @Override
    protected void after(JoinPoint joinPoint, Object result) throws Throwable {
        String methodName = joinPoint.getTargetMethod().getName();
        if (result != null)
            logger.info("{} 方法执行结束了，方法运行结果是：{}", methodName, result);
        else
            logger.info("{} 方法执行结束了", methodName);
    }

    @Override
    protected void throwable(JoinPoint joinPoint, Throwable e) {
        String methodName = joinPoint.getTargetMethod().getName();
        logger.info("{} 方法抛出了异常", methodName, e);
    }
}
