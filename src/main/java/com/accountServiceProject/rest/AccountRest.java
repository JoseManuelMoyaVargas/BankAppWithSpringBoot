package com.accountServiceProject.rest;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.accountServiceProject.dao.AccountDAO;
import com.accountServiceProject.dao.TransferDAO;
import com.accountServiceProject.model.Account;
import com.accountServiceProject.model.Transfer;

@Controller
@CrossOrigin(origins="*")
public class AccountRest {
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private TransferDAO transferDAO;
	
	@GetMapping("/")
	public String getMain(Model model) {
		model.addAttribute("accounts",accountDAO.findAll());
		return "index";
	}
	
	@RequestMapping("/newAccount")
    public String newAccount(Model model){
        model.addAttribute("account", new Account());
        return "addAccount";
    }
	
	@PostMapping("/saveAccount")
	public String saveAccount(Model model,Account account,BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "addAccount";
		}
		accountDAO.save(account);
		return "redirect:/";
	}
	
	@RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
    	Account acc = accountDAO.findById(id).orElse(null);
        model.addAttribute("account",acc);
        return "editAccount";
    }
	
	@RequestMapping("delete/{id}")
    public String delete(@PathVariable Integer id){
    	Account acc = accountDAO.findById(id).orElse(null);
    	transferDAO.deleteAccount(acc);
        accountDAO.delete(acc);
        return "redirect:/";
    }
	@RequestMapping("/find")
	public String find(Model model,@Param("searchInput") String searchInput,@Param("selectorSearch") String selectorSearch) {
		if(searchInput!="") {
			model.addAttribute("searchInput", searchInput);
			model.addAttribute("selectorSearch",selectorSearch);
			switch(selectorSearch) {
			  case "name":
				model.addAttribute("accounts",accountDAO.findByName(searchInput));
			    break;
			  case "currency":
				 model.addAttribute("accounts",accountDAO.findByCurrency(searchInput));
			    break;
			}
		}else {
			return "redirect:/";
		}
		
		return "index";
	}
	
	@RequestMapping("/newTransfer")
	public String newTransfer(Model model) {
		model.addAttribute("transfer", new Transfer());
		model.addAttribute("errorText","");
		model.addAttribute("accounts",accountDAO.findAll());
	    return "transfer";
	}
	
	@PostMapping("/saveTransfer")
	public String saveTransfer(Model model,Transfer transfer,BindingResult bindingResult) {
	
		if (bindingResult.hasErrors()) {
			model.addAttribute("accounts",accountDAO.findAll());
			return "transfer";
		}
		Account accOrigin = transfer.getAccOrigin();
		Account accDestination = transfer.getAccDestination();
		BigDecimal amount = transfer.getAmount();
		
		if(accOrigin.getTreasury()==false && accOrigin.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)==-1) {
			model.addAttribute("errorText","Non treasury accounts can not have a negative balance!");
			model.addAttribute("accounts",accountDAO.findAll());
			return "transfer";
		}
		
		if(accOrigin.getCurrency()!=accDestination.getCurrency()) {
			model.addAttribute("errorText","Transfers must be between accounts with the same currency!");
			model.addAttribute("accounts",accountDAO.findAll());
			return "transfer";
		}
		
		/* Do the math */
		accOrigin.setBalance(accOrigin.getBalance().subtract(amount));
		accDestination.setBalance(accDestination.getBalance().add(amount));
		accountDAO.save(accOrigin);
		accountDAO.save(accDestination);
		transferDAO.save(transfer);

		return "redirect:/";
	}

}
