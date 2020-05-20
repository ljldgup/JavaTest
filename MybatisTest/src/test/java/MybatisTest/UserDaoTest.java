package MybatisTest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class UserDaoTest {

    public UserDao userDao;
    public SqlSession sqlSession;

    @Before
    public void setUp() throws Exception {
        // mybatis-config.xml
        String resource = "mybatis-config.xml";
        // 读取配置文件
        InputStream is = Resources.getResourceAsStream(resource);
        // 构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        
        // 获取sqlSession
        sqlSession = sqlSessionFactory.openSession();

        //this.userDao = new UserDaoImpl(sqlSession);
        this.userDao = sqlSession.getMapper(UserDao.class);
        
    }

    /**
		     * 一级缓存满足条件：
		1、同一个session中
		2、相同的SQL和参数
     */
    @Test
    public void queryUserById() throws Exception {
        System.out.println(this.userDao.queryUserById("1"));
        //清空缓存
        //sqlSession.clearCache();
        System.out.println(this.userDao.queryUserById("1"));
    }

    @Test
    public void queryUserAll() throws Exception {
        List<User> userList = this.userDao.queryUserAll();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void insertUser() throws Exception {
        User user = new User();
        user.setAge(16);
        user.setBirthday(new Date("1990/09/02"));
        user.setName("大鹏");
        user.setPassword("123456");
        user.setSex(1);
        user.setUserName("evan");
        this.userDao.insertUser(user);
        this.sqlSession.commit();
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setBirthday(new Date());
        user.setName("静鹏");
        user.setPassword("654321");
        user.setSex(1);
        user.setUserName("evanjin");
        user.setId("1");
        this.userDao.updateUser(user);
        this.sqlSession.commit();
    }

    @Test
    public void deleteUser() throws Exception {
        this.userDao.deleteUser("4");
        this.sqlSession.commit();
    }
    
    @Test
    public void queryUserByUserName1() throws Exception {
        System.out.println(this.userDao.queryUserListByName1("hj"));
    }

    @Test
    public void queryUserByUserName2() throws Exception {
        System.out.println(this.userDao.queryUserListByName2("evan"));
    }
    
    @Test
    public void queryUserListByIds() throws Exception {
        List<User> users = this.userDao.queryUserListByIds(new String[]{"1","2"});
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testCache() throws Exception {
        System.out.println(this.userDao.queryUserById("1"));

        sqlSession.close();
        setUp();

        System.out.println(this.userDao.queryUserById("1"));
    }

    @After
    public void close() {
    	if(sqlSession != null)
    		sqlSession.close();
    }

}


