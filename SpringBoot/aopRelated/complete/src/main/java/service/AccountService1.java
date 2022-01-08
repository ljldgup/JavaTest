package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.Account1;

public interface AccountService1 extends IService<Account1> {
	void transactionFrom1stTo2nd(int num) throws Exception;
	void transactionTo2nd(int num) throws Exception;
}

