package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static jdbc.DbConnect.getConnection;

public class PartitionTest {
    private static final String createPartitionSql = "CREATE TABLE %s PARTITION OF patrol_stick_data FOR VALUES FROM ( '%s' ) TO ( '%s' )";
    private static final String checkPartitionSql = "select count(1) from pg_class where relname = '%s'";
    private static final String PATROL_STICK_DATA = "patrol_stick_data";

    public final static DateFormat YYYYMM = new SimpleDateFormat("yyyyMM");
    public final static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");


    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //下个月1号作为起始日期
        Date start = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date end = calendar.getTime();
        //日期后缀
        String suffix = YYYYMM.format(start);
        String tableName = PATROL_STICK_DATA + "_" + suffix;

        //下个月与下下个月1号分别作为起止日期
        String startDate = YYYY_MM_DD.format(start);
        String endDate =  YYYY_MM_DD.format(end);
        String createSql = String.format(createPartitionSql, tableName, startDate, endDate);
        String checkSql = String.format(checkPartitionSql, tableName);
        try (Connection conn = getConnection(); Statement statement = conn.createStatement()) {
            ResultSet set = statement.executeQuery(checkSql);
            if(set.next()){
                int count = set.getInt(1);
                if(count == 0) {
                    statement.executeQuery(createSql);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
