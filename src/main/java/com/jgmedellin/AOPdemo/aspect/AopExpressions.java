package com.jgmedellin.AOPdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {

  // create a pointcut expression to reuse it in multiple advices
  @Pointcut("execution(* com.jgmedellin.AOPdemo.dao.*.*(..))")
  public void forDaoPackage() {} // name of the pointcut declaration

  // create a pointcut for getter methods
  @Pointcut("execution(* com.jgmedellin.AOPdemo.dao.*.get*(..))")
  public void getter() {}

  // create a pointcut for setter methods
  @Pointcut("execution(* com.jgmedellin.AOPdemo.dao.*.set*(..))")
  public void setter() {}

  // create a pointcut: include package ... exclude getter/setter
  @Pointcut("forDaoPackage() && !(getter() || setter())")
  public void forDaoPackageNoGetterSetter() {}

}
