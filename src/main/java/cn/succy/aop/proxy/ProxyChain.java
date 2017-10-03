package cn.succy.aop.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 链式代理，使用责任链模式 当一个目标方法有多个切面时，先依次执行各个切面，再执行目标方法
 * @author Succy
 * @date 2017-09-30 10:20
 * @since 1.0.0
 **/

public class ProxyChain {
    private final Class<?> targetClass;
    private final Object targetObj;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<Proxy>();
    // 作为一个代理链的计数器
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObj, Method targetMethod, MethodProxy methodProxy,
                      Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObj = targetObj;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    /**
     * 执行链式代理，实际上是使用递归的方式依次将链上的各个代理对象进行执行
     * @return 返回目标方法的执行结果
     * @throws Throwable
     */
    public Object doProxyChain() throws Throwable {
        Object result;
        if (proxyIndex < proxyList.size()) {
            result = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(targetObj, methodParams);
        }
        return result;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObj() {
        return targetObj;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }
}
