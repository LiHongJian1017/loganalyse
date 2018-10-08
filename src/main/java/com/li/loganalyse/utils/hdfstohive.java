package com.li.loganalyse.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created By HongjianLi
 */
public class hdfstohive {
    private static String driver = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://192.168.80.141:10000/default";
    private static String user = "root"; //一般情况下可以使用匿名的方式，在这里使用了root是因为整个Hive的所有安装等操作都是root
    private static String password = "123456";


    public void loadtohive(){
        ResultSet res = null;
        try {
            /**
             * 第一步：把JDBC驱动通过反射的方式加载进来
             */
            Class.forName(driver);

            /**
             * 第二步：通过JDBC建立和Hive的连接器，默认端口是10000，默认用户名和密码都为空
             */
            Connection conn = DriverManager.getConnection(url, user, password);

            /**
             * 第三步：创建Statement句柄，基于该句柄进行SQL的各种操作；
             */
            Statement stmt = conn.createStatement();

            /**
             * 接下来就是SQL的各种操作；
             * 第4.1步骤：建表Table,如果已经存在的话就要首先删除；
             */
            String tableName = "data";
            //stmt.execute("drop table if exists " + tableName);


            //stmt.execute("create table " + tableName + " (id int, name string)" + "ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n'");
            /**
             *  第4.2步骤：查询建立的Table；
             */
//            String sql = "show tables '" + tableName + "'";
//            System.out.println("Running: " + sql);
//            res = stmt.executeQuery(sql);
//            if (res.next()) {
//                System.out.println(res.getString(1));
//            }
            /**
             *  第4.3步骤：查询建立的Table的schema；
             */
//            sql = "describe " + tableName;
//            System.out.println("Running: " + sql);
//            res = stmt.executeQuery(sql);
//            while (res.next()) {
//                System.out.println(res.getString(1) + "\t" + res.getString(2));
//            }

            /**
             *  第4.4步骤：加载数据进入Hive中的Table；
             */
            String filepath = "/outdata/data-r-00000";
            String sql = "load data inpath '" + filepath + "' into table " + tableName;
            System.out.println("Running: " + sql);
            stmt.execute(sql);

            /**
             *  第4.5步骤：查询进入Hive中的Table的数据；
             */
//            sql = "select * from " + tableName;
//            System.out.println("Running: " + sql);
//            res = stmt.executeQuery(sql);
//            while (res.next()) {
//                System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
//            }

            /**
             *  第4.6步骤：Hive中的对Table进行统计操作；
             */
//            sql = "select count(1) from " + tableName;   //在执行select count(*) 时候会生成mapreduce 操作  ，那么需要启动资源管理器 yarn  ： start-yarn.sh
//            System.out.println("Running: " + sql);
//            res = stmt.executeQuery(sql);
//
//            while (res.next()) {
//                System.out.println("Total lines :" + res.getString(1));
//            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ResultSet res = null;

        try {
            /**
             * 第一步：把JDBC驱动通过反射的方式加载进来
             */
            Class.forName(driver);

            /**
             * 第二步：通过JDBC建立和Hive的连接器，默认端口是10000，默认用户名和密码都为空
             */
            Connection conn = DriverManager.getConnection(url, user, password);

            /**
             * 第三步：创建Statement句柄，基于该句柄进行SQL的各种操作；
             */
            Statement stmt = conn.createStatement();

            /**
             * 接下来就是SQL的各种操作；
             * 第4.1步骤：建表Table,如果已经存在的话就要首先删除；
             */
            String tableName = "data";
            stmt.execute("drop table if exists " + tableName);


            stmt.execute("create table " + tableName + " (id int, name string)" + "ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n'");
            /**
             *  第4.2步骤：查询建立的Table；
             */
            String sql = "show tables '" + tableName + "'";
            System.out.println("Running: " + sql);
            res = stmt.executeQuery(sql);
            if (res.next()) {
                System.out.println(res.getString(1));
            }
            /**
             *  第4.3步骤：查询建立的Table的schema；
             */
            sql = "describe " + tableName;
            System.out.println("Running: " + sql);
            res = stmt.executeQuery(sql);
            while (res.next()) {
                System.out.println(res.getString(1) + "\t" + res.getString(2));
            }

            /**
             *  第4.4步骤：加载数据进入Hive中的Table；
             */
            String filepath = "/outdata/data-r-00000";
            sql = "load data inpath '" + filepath + "' into table " + tableName;
            System.out.println("Running: " + sql);
            stmt.execute(sql);

            /**
             *  第4.5步骤：查询进入Hive中的Table的数据；
             */
            sql = "select * from " + tableName;
            System.out.println("Running: " + sql);
            res = stmt.executeQuery(sql);
            while (res.next()) {
                System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
            }

            /**
             *  第4.6步骤：Hive中的对Table进行统计操作；
             */
            sql = "select count(1) from " + tableName;   //在执行select count(*) 时候会生成mapreduce 操作  ，那么需要启动资源管理器 yarn  ： start-yarn.sh
            System.out.println("Running: " + sql);
            res = stmt.executeQuery(sql);

            while (res.next()) {
                System.out.println("Total lines :" + res.getString(1));
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
