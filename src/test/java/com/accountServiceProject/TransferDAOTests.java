package com.accountServiceProject;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.accountServiceProject.dao.TransferDAO;

@DataJpaTest
public class TransferDAOTests {
	@Autowired
	private TransferDAO transferDAO;
	
	@BeforeEach 
	public void init() {
		
	}
}
