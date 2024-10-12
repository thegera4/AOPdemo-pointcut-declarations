package com.jgmedellin.AOPdemo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MembershipDAOImpl implements MembershipDAO{

  @Override
  public void addAccount() {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING A MEMBERSHIP ACCOUNT");
  }

  @Override
  public void addSillyMember() {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING A SILLY MEMBERSHIP ACCOUNT");
  }

  @Override
  public boolean addCheckMember() {
    System.out.println(getClass() + ": DOING MY DB WORK: CHECKING IF USER IS A MEMBER");
    return true;
  }

}