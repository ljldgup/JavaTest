package bean;

import entity.Account1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.AccountService1;

import java.util.List;

@RestController
@RequestMapping("/account")
public class TransactionRelated {
	@Autowired
	private AccountService1 accountService;

	@GetMapping("list")
	public  List<Account1> getAccountList(){
		return accountService.list();
	}

	@GetMapping("transaction")
	public String transaction(@RequestParam(value = "num", defaultValue = "11") int num){
		try {
			accountService.transactionFrom1stTo2nd(num);
			return "成功";
		}catch (Exception e){
			e.printStackTrace();
			return e.getMessage();
		}

	}
}
