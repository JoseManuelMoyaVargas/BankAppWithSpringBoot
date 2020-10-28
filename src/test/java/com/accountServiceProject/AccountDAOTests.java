package com.accountServiceProject;

import com.accountServiceProject.dao.AccountDAO;
import com.accountServiceProject.model.Account;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@DataJpaTest
public class AccountDAOTests {
	@Autowired
	private AccountDAO accountDAO;
	
	@BeforeEach 
    public void init() {
		//given
		Account acc1 = new Account();
		acc1.setBalance(new BigDecimal(100.0)); acc1.setCurrency(Currency.getInstance("USD"));
		acc1.setName("Pablo");acc1.setTreasury(false);
		
		Account acc2 = new Account();
		acc2.setBalance(new BigDecimal(1000.0)); acc2.setCurrency(Currency.getInstance("USD"));
		acc2.setName("Jose");acc2.setTreasury(false);
		
		Account acc3 = new Account();
		acc3.setBalance(new BigDecimal(200.0)); acc3.setCurrency(Currency.getInstance("USD"));
		acc3.setName("Marta");acc3.setTreasury(true);
		
		accountDAO.save(acc1); 	accountDAO.save(acc2); 	accountDAO.save(acc3);
    }
	
	@Test
	public void testFindAll() {
		List<Account> accounts = accountDAO.findAll();
		assertEquals(3,accounts.size());
	}
	@Test
	public void testFindByName() {
		Account foundAccount = accountDAO.findByName("Pa").get(0);
		assertEquals("Pablo",foundAccount.getName());
	}
	@Test
	public void testFindByNameAfterDeletion() {
		accountDAO.deleteById(1);
		List<Account> accounts= accountDAO.findByName("Pa");
		assertEquals(0,accounts.size());
	}
	@Test
	public void testFindMoreThanOneByName() {
		List<Account> foundAccounts= accountDAO.findByName("o");
		assertEquals(2,foundAccounts.size());
	}
	@Test
	public void testFindByCurrency1() {
		List<Account> foundAccounts= accountDAO.findByCurrency("USD");
		assertEquals(3,foundAccounts.size());
	}
	@Test
	public void testFindByCurrency2() {
		List<Account> foundAccounts= accountDAO.findByCurrency("U");
		assertEquals(3,foundAccounts.size());
	}
}
