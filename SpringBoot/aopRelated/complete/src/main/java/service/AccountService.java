package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.Account;

public interface AccountService extends IService<Account> {
	void transactionTest1(int num) throws Exception;
	void transactionTest2(int num) throws Exception;
}
