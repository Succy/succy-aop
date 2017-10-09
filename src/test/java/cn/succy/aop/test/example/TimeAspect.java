package cn.succy.aop.test.example;

import cn.succy.aop.proxy.AbstractAspectProxy;
import cn.succy.aop.proxy.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 记录方法执行耗费时间的切面
 *
 * @author Succy
 * @date 2017-10-09 16:35
 **/

public class TimeAspect extends AbstractAspectProxy {
    private static final Logger logger = LoggerFactory.getLogger(TimeAspect.class);
    private long start = System.currentTimeMillis();

    @Override
    protected void before(JoinPoint joinPoint) throws Throwable {
        start = System.currentTimeMillis();
    }

    @Override
    protected void after(JoinPoint joinPoint, Object result) throws Throwable {
        long end = System.currentTimeMillis();
        String methodName = joinPoint.getTargetMethod().getName();
        logger.info("{} 方法耗时{}ms", methodName, (end - start));
    }
}
