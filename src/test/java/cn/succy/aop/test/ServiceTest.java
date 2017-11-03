package cn.succy.aop.test;

import org.junit.Test;

import java.lang.annotation.Target;

/**
 * @author Succy
 * @date 2017-11-01 18:23
 **/

public class ServiceTest {
    @Test
    public void test() throws Exception {
        Class<?> clazz = Class.forName("cn.succy.aop.test.Service");
        Service service = (Service) clazz.newInstance();
        String result = service.login("Succy", "123456");
        System.out.println(result);
    }
}
