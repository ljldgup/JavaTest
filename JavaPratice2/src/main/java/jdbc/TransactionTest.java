package jdbc;

import java.sql.*;
import java.util.UUID;

import static jdbc.DbConnect.getConnection;

public class TransactionTest {
    public static void main(String[] args) throws Exception {
        String sql = "insert into public.patrol_stick_data(patrol_card_code, patrol_stick_code, patrol_date, create_date)  VALUES(?,?,?,?)";
        Timestamp now = new Timestamp(new java.util.Date().getTime());
        try (Connection conn= getConnection(); PreparedStatement ps = conn.prepareStatement(sql);){
            //事务
            conn.setAutoCommit(false);
            for(int i = 0; i < 100; i++){
                ps.setString(1, "fake");
                ps.setString(2, "fake " + UUID.randomUUID());
                ps.setTimestamp(3, now);
                ps.setTimestamp(4, now);
                ps.addBatch();
                if (i % 10 == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            ps.executeBatch();
            conn.commit();
        }catch(Exception e){

        }

    }
}
