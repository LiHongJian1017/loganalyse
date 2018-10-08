package com.li.loganalyse.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyMap extends Mapper<LongWritable, Text, LongWritable, Text> {

	private static final long serialVersionUID = 5608532423753884772L;
	//	待转换字符串日期格式
	private static String parse_format = "[dd/MMM/yyyy:HH:mm:ss Z]";
	//	待转换日期时区类型
	private static Locale locale = Locale.US;
	//	需要转换目标日期格式
	private static String format_format = "yyyy-MM-dd HH:mm:ss";
	public static long flag=0;
	private static Ip ip;
	private static IpUtils ipUtils;
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		try{
			flag++;
			String ipres=null;
			String timeres=null;
			String dayres=null;
			String liuliangres=null;
			String typeres=null;
			String numres=null;
			ip = new Ip();
			ipUtils=new IpUtils();

			String str1=value.toString();
			String[] words=str1.split("\"");
			//ip
			ipres=ipUtils.ip(StringUtils.substringBefore(words[0], "-").trim());
			//ipres=ip.result(StringUtils.substringBefore(words[0], "-").trim());
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
				if(info.indexOf("/")!=-1){
					try{
						int numflag;
						String[] typeandnum=info.split("/");
						String type = typeandnum[0];
						String num = typeandnum[1];
						typeres=type;
						numflag=Integer.parseInt(num);
						numres=num;
					}catch(Exception e){
						numres=null;
					}
				}
			}
			if((typeres.equals("video")||typeres.equals("article")||typeres.equals("learn")&&(numres!=null))){
				context.write(new LongWritable(flag), new Text(ipres+"\t"+timeres+"\t"+dayres+"\t"+liuliangres+"\t"+typeres+"\t"+numres));
			}
		} catch (Exception e){
		}

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
	public static String parser(String date,String parse_format,Locale locale,String format_format){

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

