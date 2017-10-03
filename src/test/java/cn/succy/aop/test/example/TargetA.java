package cn.succy.aop.test.example;

import cn.succy.aop.proxy.Aspect;
import cn.succy.aop.proxy.PointCut;

/**
 * @author Succy
 * @date 2017-10-02 11:05
 **/
@Aspect({AspectA.class, AspectB.class})
public class TargetA {

    @PointCut({AspectC.class})
    public void test() {
        System.out.println("我是TargetA的test方法");
    }
}
