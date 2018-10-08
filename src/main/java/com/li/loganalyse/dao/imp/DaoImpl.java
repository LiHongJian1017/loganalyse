package com.li.loganalyse.dao.imp;

import com.li.loganalyse.dao.Dao;
import com.li.loganalyse.entity.One;
import com.li.loganalyse.entity.Three;
import com.li.loganalyse.entity.Two;
import com.li.loganalyse.utils.HiveJDBCUtils;
import com.li.loganalyse.utils.Loadtohive;
import com.li.loganalyse.utils.MyJob;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By HongjianLi
 */
@Repository
public class DaoImpl implements Dao {
    @Override
    public List<One> findOne() {
        Connection connection=null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<One> ones = new ArrayList<One>();
        try {
            connection = HiveJDBCUtils.getconnection();
            statement = connection.createStatement();
            String sql = "select count(1) c,type,id from data where type='video' or type='article' group by type,id";
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Integer c = resultSet.getInt("c");
                String type = resultSet.getString("type");
                String id = resultSet.getString("id");
                One one = new One(null,type,id,c);
                ones.add(one);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                HiveJDBCUtils.close(connection,statement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(ones);
        return ones;
    }

    @Override
    public List<Two> findTwo() {
        Connection connection=null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Two> twos = new ArrayList<Two>();
        try {
            connection = HiveJDBCUtils.getconnection();
            statement = connection.createStatement();
            String sql = "select ip,count(1) c,type,id from data where type='learn' group by type,ip,id";
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String type = resultSet.getString("type");
                String id = resultSet.getString("id");
                Integer c = resultSet.getInt("c");
                String ip =  resultSet.getString("ip");

                Two two = new Two(null,type,id,c,ip);
                twos.add(two);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                HiveJDBCUtils.close(connection,statement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(twos);
        return twos;
    }

    @Override
    public List<Three> findThree() {
        Connection connection=null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Three> threes = new ArrayList<Three>();
        try {
            connection = HiveJDBCUtils.getconnection();
            statement = connection.createStatement();
            String sql = "select type,id,traffic from data where type='learn' order by traffic desc limit 10";
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String type = resultSet.getString("type");
                String id = resultSet.getString("id");
                Integer traffic = resultSet.getInt("traffic");
                Three three = new Three(null,type,id,traffic);
                threes.add(three);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                HiveJDBCUtils.close(connection,statement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(threes);
        return threes;
    }
    @Override
    public void job() {
        MyJob myJob = new MyJob();
        myJob.job();
    }
    @Override
    public void load() {
        Loadtohive.load();
    }
}
