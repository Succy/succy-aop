package cn.succy.aop.test;

import cn.succy.aop.Aop;
import cn.succy.aop.test.example.TargetA;
import cn.succy.aop.test.example.TargetB;
import org.junit.Test;

/**
 * @author Succy
 * @date 2017-10-02 10:48
 **/

public class AopTest {

    @Test
    public void testTargetA() {
        TargetA targetA = Aop.enhance(TargetA.class);
        targetA.test();
        System.out.println(targetA);
    }

    @Test
    public void testTargetB() {
        TargetB targetB = Aop.enhance(TargetB.class);
        targetB.test();
        targetB.testB();
        System.out.println("-------------------");
        TargetA targetA = Aop.enhance(TargetA.class);
        targetA.test();
    }
}
