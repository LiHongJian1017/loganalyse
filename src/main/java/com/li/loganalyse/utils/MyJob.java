package com.li.loganalyse.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.net.URI;

public class MyJob extends Configured implements Tool{
	
	public static void main(String[] args) throws Exception {
		MyJob myJob=new MyJob();
		ToolRunner.run(myJob, null);
	}
	public void job(){
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.80.141:9000");

		//定义URL
		URI uri = URI.create("hdfs://192.168.80.141:9000/");
		//获取文件系统
		FileSystem fileSystem = null;
		try {
			fileSystem = FileSystem.get(uri,conf,"root");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Path path = new Path("/outdata");
		try {
			if(fileSystem.exists(path)){
                fileSystem.delete(path,true);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		Job job= null;
		try {
			job = Job.getInstance(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		job.setJarByClass(MyJob.class);
		job.setMapperClass(MyMap.class);
		job.setReducerClass(MyReduce.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		try {
			FileInputFormat.addInputPath(job, new Path("/hadoop/1_access.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputFormat.setOutputPath(job, new Path("/outdata"));
		try {
			job.waitForCompletion(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.80.141:9000");

		//定义URL
		URI uri = URI.create("hdfs://192.168.80.141:9000/");
		//获取文件系统
		FileSystem fileSystem = FileSystem.get(uri,conf,"root");
		Path path = new Path("/outdata");
		if(fileSystem.exists(path)){
			fileSystem.delete(path,true);
		}
		Job job=Job.getInstance(conf);
		job.setJarByClass(MyJob.class);
		job.setMapperClass(MyMap.class);
		job.setReducerClass(MyReduce.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path("/hadoop/1_access.log"));
		FileOutputFormat.setOutputPath(job, new Path("/outdata"));
		job.waitForCompletion(true);
        System.out.println();
		return 0;
	}
}
