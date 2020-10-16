package com.accountServiceProject;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.accountServiceProject.dao.TransferDAO;
import com.accountServiceProject.model.Account;
import com.accountServiceProject.model.Transfer;

@DataJpaTest
public class TransferDAOTests {
	@Autowired
	private TransferDAO transferDAO;
	
	@Autowired
    private TestEntityManager entityManager;
	@Test
	public void whenDeleteAccout_thenDeleted() {
		//given
		Account acc1 = new Account();
		Account acc2 = new Account();
		entityManager.persist(acc1);
		entityManager.persist(acc2);
	    entityManager.flush();
		
		Transfer tr = new Transfer();
		tr.setAccOrigin(acc1);
		tr.setAccDestination(acc2);
		entityManager.persist(tr);
		entityManager.flush();
		
		//when
		transferDAO.deleteAccount(acc1);
		List<Transfer> transfers = transferDAO.findAll();
		
		//then
		assertEquals(0,transfers.size());
		
	}
}
