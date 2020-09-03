package MybatisTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Evan
 */
public class JDBCTest {
	public static void main(String[] args) throws Exception {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		try {
			// ��������
			Class.forName("com.mysql.jdbc.Driver");
			// ��ȡ����
			String url = "jdbc:mysql://127.0.0.1:3306/ssmdemo?serverTimezone=UTC";
			String user = "springuser";
			String password = "123456";
			connection = DriverManager.getConnection(url, user, password);
			// ��ȡstatement�� preparedStatement
			String sql = "select * from tb_user";
			prepareStatement = connection.prepareStatement(sql);
			// ���ò���
			// prepareStatement.setLong(1, 1l);
			// ִ�в�ѯ
			rs = prepareStatement.executeQuery();
			// ��������
			while (rs.next()) {
				System.out.println(rs.getString("user_name"));
				System.out.println(rs.getString("name"));
				System.out.println(rs.getInt("age"));
				System.out.println(rs.getDate("birthday"));
			}
		} finally {
			// �ر����ӣ��ͷ���Դ
			if (rs != null) {
				rs.close();
			}
			if (prepareStatement != null) {
				prepareStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}
