package cn.succy.aop.test.example.annotation;

import cn.succy.aop.annotation.PointCut;
import cn.succy.aop.test.example.RoleAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PointCut(RoleAspect.class)
public @interface RolesAllow {
   String[] value();
}
