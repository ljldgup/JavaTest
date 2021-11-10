package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.Account;
import mapper.AccountMapper;
import org.springframework.stereotype.Repository;
import service.AccountService;

@Repository
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
}
