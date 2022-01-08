package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.Account2;
import mapper2.AccountMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.AccountService2;

import java.math.BigDecimal;

@Component("AccountServiceImpl2")
public class AccountServiceImpl2 extends ServiceImpl<AccountMapper2, Account2> implements AccountService2 {

	@Autowired
	ApplicationContext applicationContext;

	//嵌套事务对不同数据源无效，最好不要放在一个事务里,要调用也放在最后调用
	@Override
	@Transactional(transactionManager = "transactionManager2",
			rollbackFor = Exception.class,
			propagation = Propagation.NESTED)
	public void transactionTo2nd(int num) throws Exception {
		Account2 account = list().get(0);
		account.setAmount(account.getAmount().add(new BigDecimal(num)));
		updateById(account);
//		throw new Exception("Exception from " + Thread.currentThread().getStackTrace()[1]);
	}
}
