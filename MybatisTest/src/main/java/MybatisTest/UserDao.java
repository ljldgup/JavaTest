package MybatisTest;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserDao {

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    public User queryUserById(String id);

    /**
     * 查询所有用户信息
     *
     * @return
     */
    public List<User> queryUserAll();

    /**
     * 新增用户
     *
     * @param user
     */
    public void insertUser(User user);

    /**
     * 更新用户信息
     *
     * @param user
     */
    public void updateUser(User user);

    /**
     * 根据id删除用户信息
     *
     * @param id
     */
    public void deleteUser(String id);
    
    /**
     * #号
     * @param username1
     * @return
     */
    public User queryUserListByName1(@Param("username1") String username1);

    /**
     * $号
     * @param username2
     * @return
     */
    public List<User> queryUserListByName2(@Param("username2") String username2);

    /**
 	* 按多个Id查询
     * @param ids
     * @return
     */
    List<User> queryUserListByIds(@Param("ids") String[] ids);



}
