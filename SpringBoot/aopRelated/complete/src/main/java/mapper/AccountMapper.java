package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import entity.Account;
import org.apache.ibatis.annotations.Mapper;
//import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
