package com.tupengxiong.hadoop_wordcount;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountRemote extends Configured implements Tool {

	private static final Log logger = LogFactory.getLog(WordCountRemote.class);

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		/**
		 * map函数继承自MapReduceBase, 并且实现了Mapper接口, 此接口是一个泛型类型,它有4种形式的参数,
		 * 分别用来指定map的输入key类型, 输入value值类型, 输出key值类型和输出value值类型.
		 * 在本例中，输入使用的是TextInputFormat, 它的输出key值是LongWritable类型, 输出value值是Text类型.
		 * 根据本例, 需要输出的是<Text, IntWritable>的形式, 所以输出的key值类型是Text,
		 * 输出的value类型是IntWritable.
		 */
		@Override
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				word.set(tokenizer.nextToken());
				output.collect(word, one);
			}

		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

		/**
		 * reduce函数的输入以map的输出作对应, 因此reduce的输入类型是<Text, IntWritable>.
		 */
		@Override
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output,
				Reporter reporter) throws IOException {
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();

			}
			output.collect(key, new IntWritable(sum));

		}
	}

	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		JobConf job = new JobConf(conf);
		conf.set("mapreduce.jobtracker.address", "192.168.145.129:9001");
		conf.set("fs.defaultFS", "hdfs://192.168.145.129:9000");
		conf.set("hadoop.job.ugi", "hadoop");
		conf.set("Hadoop.tmp.dir", "/user/temp/");

		job.setJarByClass(WordCountRemote.class);
		job.setJobName("wordcount");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setInputFormat(TextInputFormat.class);
		job.setOutputFormat(TextOutputFormat.class);
		String hdfs = "hdfs://192.168.145.129:9000";
		args = new String[] { hdfs + "/user/input/", hdfs + "/user/output/WordCount/" + new Date().getTime() };
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		RunningJob runningJob = JobClient.runJob(job);
		logger.info("runningJob " + runningJob.getJobName());
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new WordCountRemote(), args);
		System.exit(ret);
	}
}