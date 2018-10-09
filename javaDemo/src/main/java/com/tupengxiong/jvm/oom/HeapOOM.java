package com.tupengxiong.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * VM args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime
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
	0.183: [GC (Allocation Failure) [PSYoungGen: 5387K->496K(6144K)] 5387K->3737K(19968K), 0.0071485 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
	0.190: Total time for which application threads were stopped: 0.0073142 seconds, Stopping threads took: 0.0000281 seconds
	0.195: [GC (Allocation Failure) [PSYoungGen: 6038K->496K(6144K)] 9280K->8288K(19968K), 0.0097738 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
	0.204: Total time for which application threads were stopped: 0.0099024 seconds, Stopping threads took: 0.0000266 seconds
	0.208: [Full GC (Ergonomics) [PSYoungGen: 6128K->0K(6144K)] [ParOldGen: 10957K->12888K(13824K)] 17085K->12888K(19968K), [Metaspace: 2666K->2666K(1056768K)], 0.1630939 secs] [Times: user=0.20 sys=0.00, real=0.16 secs] 
	0.371: Total time for which application threads were stopped: 0.1632200 seconds, Stopping threads took: 0.0000260 seconds
	0.372: [Full GC (Ergonomics) [PSYoungGen: 3562K->3000K(6144K)] [ParOldGen: 12888K->13400K(13824K)] 16450K->16401K(19968K), [Metaspace: 2666K->2666K(1056768K)], 0.1258780 secs] [Times: user=0.16 sys=0.00, real=0.13 secs] 
	0.498: [Full GC (Allocation Failure) [PSYoungGen: 3000K->3000K(6144K)] [ParOldGen: 13400K->13388K(13824K)] 16401K->16389K(19968K), [Metaspace: 2666K->2666K(1056768K)], 0.0908518 secs] [Times: user=0.13 sys=0.00, real=0.09 secs] 
	0.589: Total time for which application threads were stopped: 0.2169302 seconds, Stopping threads took: 0.0000204 seconds
	java.lang.OutOfMemoryError: Java heap space
	Dumping heap to java_pid1176.hprof ...
	0.674: Total time for which application threads were stopped: 0.0821614 seconds, Stopping threads took: 0.0000136 seconds
	Heap dump file created [28023867 bytes in 0.083 secs]
	Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
		at java.util.Arrays.copyOf(Arrays.java:3210)
		at java.util.Arrays.copyOf(Arrays.java:3181)
		at java.util.ArrayList.grow(ArrayList.java:261)
		at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:235)
		at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:227)
		at java.util.ArrayList.add(ArrayList.java:458)
		at com.tupengxiong.jvm.oom.HeapOOM.main(HeapOOM.java:48)
	Heap
	 PSYoungGen      total 6144K, used 3224K [0x00000000ff980000, 0x0000000100000000, 0x0000000100000000)
	  eden space 5632K, 57% used [0x00000000ff980000,0x00000000ffca61b8,0x00000000fff00000)
	  from space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
	  to   space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
	 ParOldGen       total 13824K, used 13388K [0x00000000fec00000, 0x00000000ff980000, 0x00000000ff980000)
	  object space 13824K, 96% used [0x00000000fec00000,0x00000000ff913268,0x00000000ff980000)
	 Metaspace       used 2697K, capacity 4486K, committed 4864K, reserved 1056768K
	  class space    used 291K, capacity 386K, committed 512K, reserved 1048576K


	 * @Title: main   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param args      
	 * @return: void      
	 * @throws
	 */
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().freeMemory()/1024/1024);
		Runtime.getRuntime().freeMemory();

		List<OOM> lists = new ArrayList<OOM>();
		while(true){
			lists.add(new OOM());
		}
	}
}
