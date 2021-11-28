package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.Account;
import mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.AccountService;

import java.math.BigDecimal;

@Service("debugAccountService")
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

	//注入自己用于生成一个新事务
	@Autowired
	ApplicationContext applicationContext;

	//打断点用
	public AccountServiceImpl() {
	}

	@Override
//	传播范围默认Propagation.REQUIRED：如果当前没有事务，就新建一个事务，如果已存在一个事务中，加入到这个事务中
	@Transactional(rollbackFor = Exception.class)
	public void transactionTest1(int num) throws Exception {
		Account account = list().get(0);
		account.setAmount(new BigDecimal(num));
		int result = this.getBaseMapper().updateById(account);
		AccountService accountService = (AccountService)applicationContext.getBean(this.getClass());
		try {
			accountService.transactionTest2(num);
		}catch (Exception e){

		}
//		throw new Exception();
	}

	@Override
	//Propagation.REQUIRES_NEW嵌套事务，1抛异常时1,2都回退，2抛异常，但用try包住则只回退2, 1提交
//	@Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)

	//Propagation.REQUIRES_NEW新独立事务，1抛异常时，不回退， 2抛异常，1未
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
	public void transactionTest2(int num) throws Exception {
		Account account = list().get(1);
		account.setAmount(new BigDecimal(num*2));
		updateById(account);
		throw new Exception();
	}
}
