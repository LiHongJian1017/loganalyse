package com.li.loganalyse.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By HongjianLi
 */
public class Loadtohive {
    public static void main(String[] args) {
load();
    }
    public static void load(){
        Connection connection=null;
        Statement statement = null;
        try {
            connection = HiveJDBCUtils.getconnection();
            statement = connection.createStatement();
            String filepath="/outdata/part-r-00000";
            String tableName="data";
            String sql = "load data inpath '" + filepath + "' into table " + tableName;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                HiveJDBCUtils.close(connection,statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
