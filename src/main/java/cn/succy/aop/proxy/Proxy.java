package cn.succy.aop.proxy;

/**
 * 代理接口
 * @author Succy
 * @since 1.0.0
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
