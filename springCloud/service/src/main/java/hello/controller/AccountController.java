package hello.controller;

import java.math.BigDecimal;
import java.sql.SQLException;

import hello.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import hello.entity.User;

@Controller    
@RequestMapping(path = "/user") 
public class AccountController {
	@Autowired 
	private AccountService userService;

	@PostMapping(path = "/add") 
	public @ResponseBody
	String addNewUser(@RequestBody User user) {
		user.setAmount(new BigDecimal(.0));
		userService.saveOrUpdate(user);
		
		return String.valueOf(user.getId());
	}


	
	@Transactional
	
	public void rollback() {
		User user = new User();
		user.setName("TransactionalTest1");
		user.setEmail("123333");
		userService.saveOrUpdate(user);
		try {
			
			
			throw new SQLException("exception");
		} catch (Exception e) {
			System.out.println(e);
		}
		user = new User();
		user.setName("TransactionalTest2");
		user.setEmail("1234");
		userService.save(user);
		userService.removeById(user.getId());
	}

	@GetMapping(path = "/all")
	public @ResponseBody
	Iterable<User> getAllUsers() {
		
		return userService.list();
	}


}
