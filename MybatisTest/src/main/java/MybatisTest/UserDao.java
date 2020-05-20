package MybatisTest;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserDao {

    /**
     * ����id��ѯ�û���Ϣ
     *
     * @param id
     * @return
     */
    public User queryUserById(String id);

    /**
     * ��ѯ�����û���Ϣ
     *
     * @return
     */
    public List<User> queryUserAll();

    /**
     * �����û�
     *
     * @param user
     */
    public void insertUser(User user);

    /**
     * �����û���Ϣ
     *
     * @param user
     */
    public void updateUser(User user);

    /**
     * ����idɾ���û���Ϣ
     *
     * @param id
     */
    public void deleteUser(String id);
    
    /**
     * #��
     * @param username1
     * @return
     */
    public User queryUserListByName1(@Param("username1") String username1);

    /**
     * $��
     * @param username2
     * @return
     */
    public List<User> queryUserListByName2(@Param("username2") String username2);

    /**
 	* �����Id��ѯ
     * @param ids
     * @return
     */
    List<User> queryUserListByIds(@Param("ids") String[] ids);



}
