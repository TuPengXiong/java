package com.tupengxiong.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * VM args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @ClassName:  HeapOOM   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年5月14日 下午10:01:40   
 * @version V1.0
 */
public class HeapOOM {

	private static class OOM{
		
	}
	
	/**
	 * 
	18
	java.lang.OutOfMemoryError: Java heap space
	Dumping heap to java_pid43708.hprof ...
	Heap dump file created [27991100 bytes in 0.168 secs]
	Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Unknown Source)
	at java.util.Arrays.copyOf(Unknown Source)
	at java.util.ArrayList.grow(Unknown Source)
	at java.util.ArrayList.ensureExplicitCapacity(Unknown Source)
	at java.util.ArrayList.ensureCapacityInternal(Unknown Source)
	at java.util.ArrayList.add(Unknown Source)
	at com.tupengxiong.jvm.oom.HeapOOM.main(HeapOOM.java:25)

	 * @Title: main   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param args      
	 * @return: void      
	 * @throws
	 */
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().freeMemory()/1024/1024);
		List<OOM> lists = new ArrayList<OOM>();
		Runtime.getRuntime().freeMemory();
		while(true){
			lists.add(new OOM());
		}
	}
}
