package cn.succy.aop.test;

import cn.succy.aop.Aop;
import cn.succy.aop.test.example.DemoService;
import org.junit.Test;

/**
 * @author Succy
 * @date 2017-10-02 10:48
 **/

public class AopTest {
    @Test
    public void testDemoService() {
        DemoService service = Aop.enhance(DemoService.class);
        service.login("succy", "123456");
        service.buy();

        service.add();
        service.logout();


    }
}
