package hello.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hello.entity.User;
import hello.mapper.UserMapper;
import hello.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {
}
