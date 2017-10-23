package cn.succy.aop.test.example;

import cn.succy.aop.annotation.Aspect;
import cn.succy.aop.annotation.PointCut;
import cn.succy.aop.test.example.annotation.RolesAllow;

/**
 * 模拟一个service，在上面加入切面进行拦截
 *
 * @author Succy
 * @date 2017-10-09 16:32
 **/
@Aspect({LogAspect.class, TestAspect.class})
public class DemoService {

    @RolesAllow({"SuperAdmin", "Admin"})
    public String login(String username, String pwd) {
        System.out.println(username + "用户登录了");
        return String.format("用户%s登录成功，登录密码是%s", username, pwd);
    }

    public void logout() {
        System.out.println("用户退出登录成功");
    }

    @PointCut(TimeAspect.class)
    public void buy() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("购买成功");
    }

    public void add() {
        System.out.println("添加库存");
        throw new RuntimeException("添加库存失败");
    }
}
