package com.li.loganalyse.utils;

import com.li.loganalyse.dao.Dao;
import com.li.loganalyse.dao.imp.DaoImpl;
import com.li.loganalyse.entity.One;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created By HongjianLi
 */
public class test {
    private static final long serialVersionUID = 5608532423753884772L;
    //	待转换字符串日期格式
    private static String parse_format = "[dd/MMM/yyyy:HH:mm:ss Z]";
    //	待转换日期时区类型
    private static Locale locale = Locale.US;
    private static Ip ip;
    //	需要转换目标日期格式
    private static String format_format = "yyyy-MM-dd HH:mm:ss";
    public static void main(String[] args) throws IOException {
        MyJob myJob = new MyJob();
       myJob.job();
       Loadtohive.load();
        Dao dao = new DaoImpl();
       JDBCUtil.insert3(dao.findThree());

//        IpUtils ipUtils = new IpUtils();
//        //ipUtils.ip("10.100.0.1");
//        System.out.println(ipUtils.ip("120.52.94.105"));
//        Dao dao = new DaoImpl();
//        List<One> ones=dao.findOne();
//        JDBCUtil.insert1(ones);
//        for (int i=0;i< ones.size();i++)
//        {
//            JDBCUtil.insert1(ones.get(i));
//        }

        //findAllDept();
//        String ipres=null;
//        String timeres=null;
//        String dayres=null;
//        String liuliangres=null;
//        String typeres=null;
//        String numres=null;
//        ip = new Ip();
//
//        System.out.println( ip.result("183.162.52.7"));
//
//        String str1="122.234.145.54 - - [10/Nov/2016:00:01:02 +0800] \"GET /course/programdetail/pid/45 HTTP/1.1\" 200 10760 \"www.neusoft.com\" \"http://www.neusoft.com/course/program\" - \"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.1708.400 QQBrowser/9.5.9635.400\" \"-\" 10.100.136.65:80 200 0.306 0.306\n";
//        String[] words=str1.split("\"");
//        //ip
//        ipres=ip.result(StringUtils.substringBefore(words[0], "-").trim());
//        //time
//        timeres=parser("["+splitData(words[0],"[","]")+"]");
//        //day
//        dayres=splitData(words[0],"[","/").substring(0,2);
//        //流量
//        String[] liuliang=words[2].split(" ");
//        liuliangres=liuliang[2];
//        //类型和编号
//        if(words[5].indexOf("com")!=-1)
//        {
//            int index =getCharacterPosition(words[5],"com",1)+4;
//            String info=words[5].substring(index);
//            if(info.indexOf("/")!=-1){
//                int numflag;
//                String[] typeandnum=info.split("/");
//                String type = typeandnum[0];
//                String num = typeandnum[1];
//                typeres=type;
//                try{
//                    numflag=Integer.parseInt(num);
//                    numres=num;
//                }catch(Exception e){
//                    numres=null;
//                }
//            }
//        }
//
//        System.out.println(ipres+" "+timeres+" "+dayres+" "+liuliangres+" "+typeres+" "+numres);
    }
    /**
     * 获取指定字符串中指定标识符出现的索引位置
     * */
    private static   int  getCharacterPosition(String line,String operation,int index){
        Matcher slashMatcher= Pattern.compile(operation).matcher(line);
        int mIndex=0;
        while(slashMatcher.find()){
            mIndex++;
            if(mIndex==index){
                break;
            }
        }
        return slashMatcher.start();
    }

    public static String splitData(String str, String strStart, String strEnd) {
        String tempStr;
        tempStr = str.substring(str.indexOf(strStart) + 1, str.lastIndexOf(strEnd));
        return tempStr;
    }
    public static String parser(String date, String parse_format, Locale locale, String format_format){

        String strDate = "";
        SimpleDateFormat parser = new SimpleDateFormat(parse_format,locale);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            strDate = formatter.format(parser.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            strDate = formatter.format(new Date());
        }
        return strDate;
    }
    /**
     * @description 默认解析日期格式，由[dd/MMM/yyyy:HH:mm:ss Z](如[09/Apr/2018:18:16:47 +0800])格式转为yyyy-MM-dd HH:mm:ss(如2018-04-09 18:16:47)格式
     * @param date 需要转换的日期
     * @return
     */
    public static String parser(String date){
        return parser(date, parse_format,locale,format_format);
    }


        public static List<One> findAllDept() {
            Connection connection=null;
            Statement statement = null;
            ResultSet resultSet = null;
            List<One> depts = new ArrayList<One>();
            try {
                connection = HiveJDBCUtils.getconnection();
                statement = connection.createStatement();
                String sql = "select count(1) c,type,id from data group by type,id";
                resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    System.out.println(resultSet.getString("c")+" "+resultSet.getString("type")+" "+resultSet.getString("id"));
                    //String deptno = resultSet.getString("deptno");
                    //String dname = resultSet.getString("dname");
                    //String loc = resultSet.getString("loc");
                   // One one = new One();
                    //one.setCishu(Integer.parseInt(resultSet.getString("count(1)")));
                   // depts.add(dept);
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
            return depts;
        }

}
