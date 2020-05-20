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
        // ��ȡ�����ļ�
        InputStream is = Resources.getResourceAsStream(resource);
        // ����SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        
        // ��ȡsqlSession
        sqlSession = sqlSessionFactory.openSession();

        //this.userDao = new UserDaoImpl(sqlSession);
        this.userDao = sqlSession.getMapper(UserDao.class);
        
    }

    /**
		     * һ����������������
		1��ͬһ��session��
		2����ͬ��SQL�Ͳ���
     */
    @Test
    public void queryUserById() throws Exception {
        System.out.println(this.userDao.queryUserById("1"));
        //��ջ���
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
        user.setName("����");
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
        user.setName("����");
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


