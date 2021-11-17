package hello.controller;

import hello.entity.User;
import hello.params.Transfer;
import hello.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(path="/user")
public class TransferController {

	@Autowired
	private AccountService userService;

	@PostMapping(path = "transfer")
	public String transfer(@RequestBody Transfer transfer){
		try{
			User from = userService.getById(transfer.getFrom());
			if(from.getAmount().compareTo(transfer.getAmount()) < 0){
				throw new Exception("余额不足");
			}

			TimeUnit.SECONDS.sleep(3);
			User to = userService.getById(transfer.getTo());
			from.setAmount(from.getAmount().subtract(transfer.getAmount()));
			to.setAmount(to.getAmount().add(transfer.getAmount()));
			userService.save(from);
			userService.save(to);

			return "转账成功";
		}catch (Exception e){
			return "转账失败";
		}
	}
}
