package cn.succy.aop.test;

import cn.succy.aop.Aop;
import cn.succy.aop.test.example.DemoService;

/**
 * @author Succy
 * @date 2017-11-01 18:22
 **/

public class Service {
    private DemoService demoService = Aop.enhance(DemoService.class);

    public Service() {

    }

    public String login(String username, String pwd) {
        return demoService.login(username, pwd);
    }
}
