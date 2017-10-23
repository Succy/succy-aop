# 一个基于CGLIB的简易AOP框架

## 前言
现在的aop框架应该很多了，比较出名的有Spring的aop，为什么在那么多的aop框架盛行下，我还要再写一个轮子呢？
这肯定是有一定的原因的，那要从一个老项目说起，这是一个不依赖Spring框架的老项目，里边没有IOC没有AOP，直到某一天，它需要对里边的代码进行重构，抽出一些重复的代码，一些可以作为一个切面看待的代码，但是项目又不是依赖Spring之类的框架，没有现成的AOP可以使用，那怎么办呢？引入Spring不行吗？其实我也想的，只不过引入一个体积这么大的框架，只是为了实现这么一个切面的功能，未免太浪费了吧？因此，该小框架应运而生了。

## 搭建环境

- JDK1.6
- Cglib3.2.5
- maven3

使用jdk1.6实际上就是为了兼容一些老的项目

## 如何使用？
该框架假定一个目标类有多个切面类，目标类和切面类之间是一对多的关系，因此，直到所有的切面类都执行结束之后，再执行真正的目标方法，所以，这里使用的是责任链模式来控制对各个切面的执行，具体的实现可以直接看源码。下面说一下怎么使用该框架。

1. 首先将框架引入，使用的是SLF4J作为日志门面，可以添加对于log4j或者logback的配置和实现类支持其日志的输出。
2. 编写一个切面类，继承自AbstractAspectProxy类，并重写里边需要的通知方法，比如说
```java
public class AspectTest extends AbstractAspectProxy {
    @Override
    protected void before(JoinPoint joinPoint) {
        // 前置通知的实现
    }
    
    @Override
    protected void after(JoinPoint joinPoint, Object result) {
        // 后置通知的实现
    } 
    
    @Override
    protected void throwable(JoinPoint joinPoint) {
        // 异常通知的实现
    }
    
    @Override
    protected void end() {
        // 不管是否抛出异常，都会执行的方法，就好像在finally块执行的方法
    }
    
    /**
     *  是否拦截，默认是对方法进行拦截，如果不希望方法执行的时候被框架拦截，可
     *  以重写该方法，或者是项目前期已经设定好了切面，后面不想要被拦截，但是又
     *  不想通过删除切面的方式去解除切面，也可以重写该方法，再者，如果不希望对
     *  某些方法进行拦截，亦可重写该方法。框架本身也会对目标类父类的方法进行拦
     *  截的，这点是需要注意的 
     **/
    @Override
    protected boolean isIntercept() {
        
    }
}
```
3. 编写目标类，目标类可以是已存在的类，如果想要对类中所有的方法都进行拦截(都作为切点)，那么可以在类上使用@Aspect()注解，如果想要对某个方法进行拦截，那么可以直接在要拦截的方法上使用@PointCut注解，如下
```java
// 使用了该注解，该类中所有的方法都会被AspectTest这个切面所切
@Aspect(AspectTest.class)
public class Target {
    // 该方法会被两个切面所切，一个是类级别的AspectTest
    // 一个是方法级别的AspectTest1
    @PointCut(AspectTest1.class)
    public void test() {
        
    }
    
}
```
4. 在调用目标类的方法时，需要对目标类对象进行增强，增强方法如下
```java
Target target = Aop.enhance(Target.class);
// 这样子增强之后，调用target的test方法就会触发切面的拦截，
// 从而运行切面的通知方法，起到AOP的作用
target.test();
```
5. 切面的执行顺序：Class级别>Method级别。同一个级别的情况下，配置在前面的先执行。 

具体的测试用例在test包下有，感兴趣的小伙伴们可以到代码中查看

## 鸣谢
在写这个小框架的时候，查阅了不少的资料，起初是想要实现一个类似Spring那样的用切点表达式去表示目标类集合的AOP，后面反正水平实在难以准确解析切点表达式，才使用了这种方式去实现，这个方式也借鉴了Jfinal和Smart4j这两个框架，另外，一些方法的实现和思想也借鉴了Spring框架，因此，在这里向他们的作者表示致敬和感谢。