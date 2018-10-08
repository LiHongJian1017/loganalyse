package com.li.loganalyse.utils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created By HongjianLi
 */
public class trd {
    public static Ip ip;
    private static final long serialVersionUID = 5608532423753884772L;
    //	待转换字符串日期格式
    private static String parse_format = "[dd/MMM/yyyy:HH:mm:ss Z]";
    //	待转换日期时区类型
    private static Locale locale = Locale.US;
    //	需要转换目标日期格式
    private static String format_format = "yyyy-MM-dd HH:mm:ss";
    public static void main(String[] args) throws IOException {
        String value="116.231.219.242 - - [10/Nov/2016:00:01:02 +0800] \"POST /course/ajaxmediauser/ HTTP/1.1\" 200 54 \"www.neusoft.com\" \"http://www.neusoft.com/video/6335\" mid=6335&time=60.00200000000001&learn_time=618.3 \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36\" \"-\" 10.100.136.64:80 200 0.018 0.018\n";
//        String spilt=value.toString();
//        StringBuilder text=new StringBuilder();
//        String words[]=spilt.split("\\s+");
//        text.append(words[0]+" ");
//        System.out.println(words[0]);
//        String time=spilt.substring(spilt.indexOf("[")+1,spilt.indexOf("]"));
//        System.out.println(time);
//        text.append(time+" ");
//        System.out.println(words[9]);
//        System.out.println(words[11]);
//        text.append(words[9]+" ");
//        text.append(words[11]+" ");

        String ipres=null;
        String timeres=null;
        String dayres=null;
        String liuliangres=null;
        String typeres=null;
        String numres=null;
        ip = new Ip();

        String str1=value.toString();
        String[] words=str1.split("\"");
        //ip
        ipres=ip.result(StringUtils.substringBefore(words[0], "-").trim());
        //time
        timeres=parser("["+splitData(words[0],"[","]")+"]");
        //day
        dayres=splitData(words[0],"[","/").substring(0,2);
        //流量
        String[] liuliang=words[2].split(" ");
        liuliangres=liuliang[2];
        //类型和编号
        if(words[5].indexOf("com")!=-1)
        {
            int index =getCharacterPosition(words[5],"com",1)+4;
            String info=words[5].substring(index);
            String[] typeandnum=info.split("/");
            String type = typeandnum[0];
            String num = typeandnum[1];
            //System.out.println("类型："+type);
            typeres=type;
            numres=num;
            //System.out.println("编号："+num);
        }
        System.out.println(ipres+" "+timeres+" "+dayres+" "+liuliangres+" "+typeres+" "+numres);
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
}

