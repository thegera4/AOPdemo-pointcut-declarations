package com.jgmedellin.AOPdemo;

import com.jgmedellin.AOPdemo.dao.AccountDAO;
import com.jgmedellin.AOPdemo.dao.MembershipDAO;
import com.jgmedellin.AOPdemo.service.TrafficFortuneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AoPdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AoPdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
					AccountDAO accountDAO, MembershipDAO membershipDAO, TrafficFortuneService trafficFortuneService
	) {
		return runner -> {
			//demoBeforeAdvice(accountDAO, membershipDAO);
			//demoAfterReturningAdvice(accountDAO);
			//demoAfterThrowingAdvice(accountDAO);
			//demoAfterAdvice(accountDAO);
			//demoAroundAdvice(trafficFortuneService);
			//demoAroundAdviceHandleException(trafficFortuneService);
			demoAfterAdviceRethrowException(trafficFortuneService);
		};
	}

	private void demoAfterAdviceRethrowException(TrafficFortuneService trafficFortuneService) {
		System.out.println("\n\nMain Program: AroundAdviceRethrowException");
		System.out.println("----Calling getFortune()----");
		boolean tripWire = true;
		String data = trafficFortuneService.getFortune(tripWire);
		System.out.println("My fortune is: " + data);
	}

	private void demoAroundAdviceHandleException(TrafficFortuneService trafficFortuneService) {
		System.out.println("\n\nMain Program: AroundAdviceException");
		System.out.println("----Calling getFortune()----");
		boolean tripWire = true;
		String data = trafficFortuneService.getFortune(tripWire);
		System.out.println("My fortune is: " + data);
	}

	private void demoAroundAdvice(TrafficFortuneService trafficFortuneService) {
		System.out.println("\n\nMain Program: AroundAdvice");
		System.out.println("----Calling getFortune()----");
		String data = trafficFortuneService.getFortune();
		System.out.println("My fortune is: " + data);
	}

	private void demoAfterAdvice(AccountDAO accountDAO) {
		// call method to find the accounts
		List<Account> accounts = null;

		try {
			// add a boolean flag to simulate an exception
			boolean tripWire = false;
			accounts = accountDAO.findAccounts(tripWire);
		} catch (Exception e) {
			System.out.println("\n\nMain Program ... caught exception: " + e);
		}

		// display the accounts
		System.out.println("\n\nMain Program: AfterAdvice");
		System.out.println("----");
		System.out.println(accounts);
		System.out.println("\n");
	}

	private void demoAfterThrowingAdvice(AccountDAO accountDAO) {
		// call method to find the accounts
		List<Account> accounts = null;

		try {
			// add a boolean flag to simulate an exception
			boolean tripWire = true;
			accounts = accountDAO.findAccounts(tripWire);
		} catch (Exception e) {
			System.out.println("\n\nMain Program ... caught exception: " + e);
		}

		// display the accounts
		System.out.println("\n\nMain Program: AfterThrowing");
		System.out.println("----");
		System.out.println(accounts);
		System.out.println("\n");
	}

	private void demoAfterReturningAdvice(AccountDAO accountDAO) {
		// call method to find the accounts
		List<Account> accounts = accountDAO.findAccounts();

		// display the accounts
		System.out.println("\n\nMain Program: AfterReturning");
		System.out.println("----");
		System.out.println(accounts);
		System.out.println("\n");
	}

	private void demoBeforeAdvice(AccountDAO accountDAO, MembershipDAO membershipDAO) {

		// call the business method
		Account account = new Account();
		account.setName("Madhu");
		account.setLevel("Platinum");
		accountDAO.addAccount(account, true);
		accountDAO.doWork();

		// call the accountDAO getter/setter methods
		accountDAO.setName("foobar");
		accountDAO.setServiceCode("silver");
		accountDAO.getName();
		accountDAO.getServiceCode();

		// call the membership business method
		membershipDAO.addAccount();
		membershipDAO.addSillyMember();
		membershipDAO.addCheckMember();

	}

}