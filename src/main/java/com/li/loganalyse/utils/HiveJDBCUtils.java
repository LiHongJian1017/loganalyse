package com.li.loganalyse.utils;

import java.sql.*;

/**
 * Created By HongjianLi
 */

/**
 * 工具类的封装
 * */
public class HiveJDBCUtils {
    //加载驱动
    public static String driver="org.apache.hive.jdbc.HiveDriver";
    public static String url="jdbc:hive2://192.168.80.141:10000/default";

    static {
        try{
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //获取连接
    public static Connection getconnection() throws SQLException {

          return   DriverManager.getConnection(url,"root","123456");
    }
    //关闭连接
    public static void close(Connection connection, Statement statement) throws SQLException {
        if(connection!=null){
            connection.close();
        }
        if(statement!=null){
            statement.close();
        }

    }
    public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if(connection!=null){
            connection.close();
        }
        if(statement!=null){
            statement.close();
        }
        if(resultSet!=null){
            resultSet.close();
        }
    }
}
