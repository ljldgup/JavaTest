package bean;

import entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/account")
public class TransactionRelated {
	@Autowired
	private  AccountService accountService;

	@GetMapping("list")
	public  List<Account> getAccountList(){
		return accountService.list();
	}
}
