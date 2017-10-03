package cn.succy.aop.test.example;

import cn.succy.aop.proxy.AbstractAspectProxy;
import cn.succy.aop.proxy.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Succy
 * @date 2017-10-02 11:04
 **/

public class AspectA extends AbstractAspectProxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectA.class);
    @Override
    protected void before(JoinPoint joinPoint) throws Throwable {
        logger.info("我是aspectA的before");
    }
}