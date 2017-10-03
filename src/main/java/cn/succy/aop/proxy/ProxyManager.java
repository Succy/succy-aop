package cn.succy.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器，对目标类进行增强，返回的是一个代理对象
 *
 * @author Succy
 * @date 2017-09-30 11:42
 * @since 1.0.0
 **/

public class ProxyManager {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, obj, method, methodProxy, params, proxyList).doProxyChain();
            }
        });
    }
}
