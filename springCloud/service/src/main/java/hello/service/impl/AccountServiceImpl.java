package hello.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hello.entity.User;
import hello.mapper.UserMapper;
import hello.service.AccountService;
import org.springframework.stereotype.Repository;

@Repository
public class AccountServiceImpl extends ServiceImpl<UserMapper, User> implements AccountService {
}
