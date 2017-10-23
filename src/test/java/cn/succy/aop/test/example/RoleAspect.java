package cn.succy.aop.test.example;

import cn.succy.aop.proxy.AbstractAspectProxy;
import cn.succy.aop.proxy.ProxyChain;
import cn.succy.aop.test.example.annotation.RolesAllow;
import cn.succy.aop.util.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 角色控制切面demo
 *
 * @author Succy
 * @date 2017-10-21 17:35
 **/

public class RoleAspect extends AbstractAspectProxy {
    private static final Logger logger = LoggerFactory.getLogger(RoleAspect.class);

    @Override
    protected Object around(ProxyChain chain) throws Throwable {
        Method method = chain.getTargetMethod();

        if (!method.isAnnotationPresent(RolesAllow.class)) {
            logger.error("No RolesAllow annotation");
            throw new Exception("No RolesAllow annotation");
        }

        Object result = null;
        RolesAllow annotation = method.getAnnotation(RolesAllow.class);
        String[] roles = annotation.value();
        logger.debug("roles => {}", Arrays.asList(roles));
        // 根据项目中实际情况，通过一个方法获取当前请求这个方法的User信息
        // 这里为了方便，直接假定从参数中获取
        Object[] params = chain.getMethodParams();
        String username = (String) params[0];
        String role = getRole(username);
        logger.debug("role => {}", role);

        if (ArrayUtil.contains(roles, role)) {
            result = chain.doProxyChain();
        } else {
            logger.error("你没有权限访问这个方法 => {}", method.getName());
            return "你没有权限访问这个方法";
        }
        return result;
    }

    /**
     * 模拟获取用户的角色
     *
     * @param username 用户名
     * @return 用户角色
     */
    private String getRole(String username) {
        if ("Succy".equals(username)) {
            return "SuperAdmin";
        }
        if ("Marry".equals(username)) {
            return "Admin";
        }
        // 默认游客身份
        return "Guest";
    }
}
