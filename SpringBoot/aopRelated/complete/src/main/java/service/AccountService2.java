package service;

import com.baomidou.mybatisplus.extension.service.IService;
import entity.Account1;
import entity.Account2;

public interface AccountService2 extends IService<Account2> {
	void transactionTo2nd(int num) throws Exception;
}

