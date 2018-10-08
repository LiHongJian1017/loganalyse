package com.li.loganalyse.utils;

import com.li.loganalyse.entity.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JDBCUtil {
	public static String URL="jdbc:mysql://192.168.80.141:3306/db1?useUnicode=true&characterEncoding=utf8";
	public static String USER="root";
	public static String PWD="123456";
	public static Connection getConnection(){
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL, USER, PWD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	public static void Close(Connection con,PreparedStatement pstmt,ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int insert1(List<One> ones){
		int result=0;
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into one (type,id,cishu) value (?,?,?)";
		try {
			for (int i=0;i< ones.size();i++)
			{
				pstmt=(PreparedStatement)con.prepareStatement(sql);
				pstmt.setString(1, ones.get(i).getType());
				pstmt.setString(2, ones.get(i).getId());
				pstmt.setInt(3, ones.get(i).getCishu());
				result=pstmt.executeUpdate();
				System.out.println( ones.get(i).toString());
			}

		} catch (SQLException e) {
			result=0;
			e.printStackTrace();
		}finally{
			Close(con,pstmt,null);
		}

		return result;
	}
	public static int insert2(List<Two> twos){
		int result=0;
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into two (type,id,cishu,city) value (?,?,?,?)";
		try {
			for (int i=0;i< twos.size();i++)
			{
				pstmt=(PreparedStatement)con.prepareStatement(sql);
				pstmt.setString(1, twos.get(i).getType());
				pstmt.setString(2, twos.get(i).getId());
				pstmt.setInt(3, twos.get(i).getCishu());
				pstmt.setString(4, twos.get(i).getCity());
				result=pstmt.executeUpdate();
				System.out.println( twos.get(i).toString());
			}

		} catch (SQLException e) {
			result=0;
			e.printStackTrace();
		}finally{
			Close(con,pstmt,null);
		}

		return result;
	}
	public static int insert3(List<Three> threes){
		int result=0;
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into three (type,id,traffic) value (?,?,?)";
		try {
			for (int i=0;i< threes.size();i++)
			{
				pstmt=(PreparedStatement)con.prepareStatement(sql);
				pstmt.setString(1, threes.get(i).getType());
				pstmt.setString(2, threes.get(i).getId());
				pstmt.setInt(3, threes.get(i).getTraffic());
				result=pstmt.executeUpdate();
				System.out.println( threes.get(i).toString());
			}

		} catch (SQLException e) {
			result=0;
			e.printStackTrace();
		}finally{
			Close(con,pstmt,null);
		}
		return result;
	}

	public static <T> int insert(T t){
		int result=0;
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		List<String> columns=new ArrayList<>();
		List<String> values=new ArrayList<>();
		List<Object> params=new ArrayList<>();
		Class clz= t.getClass();
		Table ann = (Table)clz.getAnnotation(Table.class);
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			if(!field.isAnnotationPresent(Id.class)){
				if(field.isAnnotationPresent(Column.class)){
					Column column= field.getAnnotation(Column.class);
					columns.add(column.value());
					values.add("?");
					field.setAccessible(true);
					try {
						params.add(field.get(t));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		String sql="insert into "+ann.value()+Arrays.toString(columns.toArray()).replaceAll("\\[", "(").replaceAll("\\]", ")")+" values"+Arrays.toString(values.toArray()).replaceAll("\\[", "(").replaceAll("\\]", ")");
		try {
			pstmt=(PreparedStatement)con.prepareStatement(sql);
			pstmt.setString(1, params.toString());
//			pstmt.setString(2, student.getSex());
//			pstmt.setString(3, student.getAge());
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Close(con,pstmt,null);
		}
		System.out.println(Arrays.toString(params.toArray()) );
		return result;
		
	}
	
	public static int executeUpdate(String sql,Object... params){
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=con.prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					pstmt.setObject(i+1, params[i]);
				}
			}
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Close(con,pstmt,null);
		}
		return result;
	}
	
	public static <T> List<T> executeQuery(String sql, RowMap<T> rowMap, Object... params){
		List<T> list=new ArrayList<>();
		Connection con=getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					pstmt.setObject(i+1, params[i]);
				}
			}
			rs=pstmt.executeQuery();
			while(rs.next()){
				T t=rowMap.rowMapping(rs);
				list.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Close(con,pstmt,rs);
		}
		return list;
	}
	
	
	
	public static int update(){
		return executeUpdate("insert into student(name,age,sex) values(?,?,?)","姓名",12,"男");
	}
	
	
}
