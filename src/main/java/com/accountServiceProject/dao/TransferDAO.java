package com.accountServiceProject.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accountServiceProject.model.Account;
import com.accountServiceProject.model.Transfer;
public interface TransferDAO extends JpaRepository<Transfer,Integer> {
		@Transactional
		@Modifying
	 	@Query("delete from Transfer t where t.accOrigin=:account or t.accDestination=:account ")
	 	public void deleteAccount(@Param("account") Account account);
 
}





