
package com.accountServiceProject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.accountServiceProject.dao.AccountDAO;
import com.accountServiceProject.model.Account;
import com.accountServiceProject.rest.AccountRest;

@SpringBootTest
class AccountUnitTests {

	@Autowired
	private AccountRest accountRest;
	
	@Mock
	AccountDAO accountDAO;
	
	@Test
	public void restCreationSuccess() throws Exception {
		assertThat(accountRest).isNotNull();
	}
	
	@Test
	public void testFindAllAccounts() {

        Account account1 = new Account();
        Account account2 = new Account();
        List<Account> listAccounts = new ArrayList<Account>();
        listAccounts.add(account1); listAccounts.add(account2);
 
        when(accountDAO.findAll()).thenReturn(listAccounts);
 
     
	}
	

}
