package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;

public class DbConnect {
    public static Connection getConnection() throws Exception {
        String sql = "insert into public.patrol_stick_data(patrol_card_code, patrol_stick_code, patrol_date, create_date)  VALUES(?,?,?,?)";
        Class.forName("org.postgresql.Driver").newInstance();
        String url ="jdbc:postgresql://192.168.15.205:15432/atm6000db_basic_service_10km";
        String user="admin";
        String password="Szagi0e*ad";
        Timestamp now = new Timestamp(new java.util.Date().getTime());
        return DriverManager.getConnection(url,user,password);
    }
}
