package com.li.loganalyse.utils;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MyReduce extends Reducer<LongWritable, Text, LongWritable, Text> {
	@Override
	protected void reduce(LongWritable key, Iterable<Text> values,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String info = null;
		for (Text text : values) {
			info=text.toString();
		}
		context.write(key, new Text(info));
	}
}
