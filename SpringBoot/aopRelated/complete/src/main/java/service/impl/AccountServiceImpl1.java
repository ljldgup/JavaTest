package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.Account1;
import lombok.extern.slf4j.Slf4j;
import mapper1.AccountMapper1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.AccountService1;
import service.AccountService2;

import java.math.BigDecimal;

@Service("debugAccountService")
@Primary
@Slf4j
public class AccountServiceImpl1 extends ServiceImpl<AccountMapper1, Account1> implements AccountService1 {

	//注入自己用于生成一个新事务
	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	@Qualifier("AccountServiceImpl2")
	AccountService2 accountService2;

	//打断点用
	public AccountServiceImpl1() {
	}

	@Override

//	Transactional 不写 rollbackfor 时RuntimeException时是会回滚的，手动抛出expection不会回滚。

	@Transactional(transactionManager = "transactionManager1",
			rollbackFor = Exception.class)
	public void transactionFrom1stTo2nd(int num) throws Exception {

		Account1 account = list().get(0);
		account.setAmount(account.getAmount().subtract(new BigDecimal(num)));
		int result = this.getBaseMapper().updateById(account);
		AccountService1 accountService1 = applicationContext.getBean(this.getClass());
		try {
//			accountService1.transactionTo2nd(num);
			accountService2.transactionTo2nd(num);
		} catch (Exception e) {
			log.info("", e);
			throw e;
		}
		throw new Exception("Exception from " + Thread.currentThread().getStackTrace()[1]);
	}


	//1外层，2内层
	//Propagation.NESTED嵌套事务，1抛异常时1,2都回退，  2抛异常，1抓到就回退

//	Transactional 不写 rollbackfor 时RuntimeException时是会回滚的，手动抛出expection不会回滚。

	//Propagation.REQUIRES_NEW 新独立事务，1抛异常时，2不回退，  2抛异常，1抓到就回退
	@Override
	@Transactional(transactionManager = "transactionManager1",
			rollbackFor = Exception.class,
			propagation = Propagation.REQUIRES_NEW)
	public void transactionTo2nd(int num) throws Exception {
		Account1 account = list().get(1);
		account.setAmount(account.getAmount().add(new BigDecimal(num)));
		updateById(account);
//		throw new Exception("Exception from " + Thread.currentThread().getStackTrace()[1]);
	}
}
