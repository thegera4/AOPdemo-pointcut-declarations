package com.jgmedellin.AOPdemo.aspect;

import com.jgmedellin.AOPdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;

//Tells Spring that this is an Aspect (class that contains advice(methods) that run for specific points in your program)
@Aspect
@Component
@Order(3) // Order of execution of the aspects
public class MyDemoLoggingAspect {

  @Around("execution(* com.jgmedellin.AOPdemo.service.*.getFortune(..))")
  public Object aroundGetFortune(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    // print out the method we are advising on
    String method = proceedingJoinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @Around on method: " + method);

    // get begin timestamp
    long begin = System.currentTimeMillis();

    // execute the method
    Object result = null;
    try {
      result = proceedingJoinPoint.proceed();
    } catch (Exception e) {
      // log the exception
      System.out.println(e.getMessage());
      // give user a custom message
      // result = "Major accident! But no worries, your private AOP helicopter is on the way!";
      // rethrow exception
      throw e;
    }

    // get end timestamp
    long end = System.currentTimeMillis();

    // compute duration and display it
    long duration = end - begin;
    System.out.println("\n=====>>> Duration: " + duration / 1000.0 + " seconds");

    return result;
  }

  @After("execution(* com.jgmedellin.AOPdemo.dao.AccountDAO.findAccounts(..))")
  public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {
    // print out which method we are advising on
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @After (finally) on method: " + method);
  }

  @AfterThrowing(
    pointcut = "execution(* com.jgmedellin.AOPdemo.dao.AccountDAO.findAccounts(..))",
    throwing = "exception"
  )
  public void afterThrowingFindAccountsAdvice(JoinPoint joinPoint, Throwable exception) {
    // print out which method we are advising on
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @AfterThrowing on method: " + method);

    // log the exception
    System.out.println("\n=====>>> The exception is: " + exception);
  }

  @AfterReturning(
    pointcut = "execution(* com.jgmedellin.AOPdemo.dao.AccountDAO.findAccounts(..))",
    returning = "result"
  )
  public void afterReturningAddAccountAdvice(JoinPoint joinPoint, List<Account> result) {
    // print out which method we are advising on
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);

    // print out the results of the method call
    System.out.println("\n=====>>> result is: " + result);

    // example of data post-process (convert the account names to uppercase)
    convertAccountNamesToUpperCase(result);

    // print out the modified results of the method call
    System.out.println("\n=====>>> modified result is: " + result);
  }

  private void convertAccountNamesToUpperCase(List<Account> result) {
    for (Account account : result) {
      account.setName(account.getName().toUpperCase());
    }
  }

  @Before("com.jgmedellin.AOPdemo.aspect.AopExpressions.forDaoPackageNoGetterSetter()")
  public void beforeAddAccountAdvice(JoinPoint joinPoint) { // JoinPoint = class with metadata about the method call
    System.out.println("\n=====>>> Executing @Before advice on addAccount()");

    // display the method signature
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    System.out.println("Method: " + methodSignature);

    // display the method arguments
    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      System.out.println(arg);
      if (arg instanceof com.jgmedellin.AOPdemo.Account) {
        Account account = (Account) arg;
        System.out.println("account name: " + account.getName());
        System.out.println("account level: " + account.getLevel());
      }
    }

  }

}