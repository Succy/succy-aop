package cn.succy.aop.test.example;

import cn.succy.aop.proxy.Aspect;
import cn.succy.aop.proxy.PointCut;

/**
 * @author Succy
 * @date 2017-10-02 17:43
 **/
@Aspect(AspectB.class)
public class TargetB {
    @PointCut(AspectA.class)
    public void test() {
        System.out.println("我是TargetB的test方法");
    }

   // @PointCut(AspectB.class)
    public void testB() {
        System.out.println("我是TargetB的testB方法");
    }
}
