package MybatisTest;

import org.apache.ibatis.session.SqlSession;
import java.util.List;

//public class UserDaoImpl implements UserDao {
public class UserDaoImpl{
    public SqlSession sqlSession;

    public UserDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    public User queryUserById(String id) {
        return this.sqlSession.selectOne("UserDao.queryUserById", id);
    }


    public List<User> queryUserAll() {
        return this.sqlSession.selectList("UserDao.queryUserAll");
    }

    public void insertUser(User user) {
        this.sqlSession.insert("UserDao.insertUser", user);
    }


    public void updateUser(User user) {
        this.sqlSession.update("UserDao.updateUser", user);
    }


    public void deleteUser(String id) {
        this.sqlSession.delete("UserDao.deleteUser", id);
    }


	public User queryUserListByName1(String username1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> queryUserListByName2(String username2) {
		// TODO Auto-generated method stub
		return null;
	}

}
