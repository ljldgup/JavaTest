package MybatisTest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class MybatisTest {
	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("user.dir"));
		// 指定全局配置文件
		String resource = "mybatis-config.xml";
		// 读取配置文件
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 构建sqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
			// 第二个参数：指定传入sql的参数：这里是用户id
			User user = sqlSession.selectOne("MyMapper.selectUser", 1);
			System.out.println(user);

			UserDao userDao = sqlSession.getMapper(UserDao.class);
			System.out.println(userDao.queryUserAll().stream().map(User::toString)
					.collect(Collectors.joining("\n")));
		} finally {
			sqlSession.close();
		}
	}
}

