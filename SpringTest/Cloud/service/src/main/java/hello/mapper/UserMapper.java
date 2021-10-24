package hello.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import hello.entity.User;
import org.apache.ibatis.annotations.Mapper;
//import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
