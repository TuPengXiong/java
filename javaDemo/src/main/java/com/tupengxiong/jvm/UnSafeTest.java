package com.tupengxiong.jvm;

import java.lang.reflect.Field;
import java.util.concurrent.locks.LockSupport;

import sun.misc.Unsafe;

/**
 * -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime
 * @ClassName: UnSafeTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tupx 
 * @date 2018年10月10日 上午11:12:45 
 *
 */
public class UnSafeTest implements Runnable{

	public static Unsafe getUnsafe() {
		try {
			// 获取到指定实例变量的在对象内存中的偏移量
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			return (Unsafe) f.get(null);
		} catch (Exception e) {
			/* ... */
		}
		return null;
	}

	private static final Unsafe unsafe = getUnsafe();

	private static final long valueOffset;

	static {
		try {
			// 获取某属性的地址【偏移量
			valueOffset = unsafe.objectFieldOffset(UnSafeTest.class.getDeclaredField("value"));
		} catch (Exception ex) {
			throw new Error(ex);
		}
	}

	private volatile int value;

	
	/**
	 * @throws InterruptedException 
	 * 
		12
	get:0
	true
	get:1
	true
	get:2
	2
	Heap
	 PSYoungGen      total 38400K, used 2663K [0x00000000d5f00000, 0x00000000d8980000, 0x0000000100000000)
	  eden space 33280K, 8% used [0x00000000d5f00000,0x00000000d6199cc8,0x00000000d7f80000)
	  from space 5120K, 0% used [0x00000000d8480000,0x00000000d8480000,0x00000000d8980000)
	  to   space 5120K, 0% used [0x00000000d7f80000,0x00000000d7f80000,0x00000000d8480000)
	 ParOldGen       total 87552K, used 0K [0x0000000081c00000, 0x0000000087180000, 0x00000000d5f00000)
	  object space 87552K, 0% used [0x0000000081c00000,0x0000000081c00000,0x0000000087180000)
	 Metaspace       used 2685K, capacity 4486K, committed 4864K, reserved 1056768K
	  class space    used 292K, capacity 386K, committed 512K, reserved 1048576K

	* @throws
	 */
	public static void main(String[] args) throws InstantiationException, InterruptedException {

		// 类中提供的3个本地方法allocateMemory、reallocateMemory、freeMemory分别用于分配内存，扩充内存和释放内存，与C语言中的3个方法对应。

		// unsafe.allocateMemory(100L);

		// unsafe.reallocateMemory(100L, 100L);

		// unsafe.freeMemory(100L);

		UnSafeTest unSafeTest = (UnSafeTest) unsafe.allocateInstance(UnSafeTest.class);

		// 地址偏移量
		System.out.println(valueOffset);

		// 获取地址偏移量对应的值
		System.out.println("get:" + unsafe.getIntVolatile(unSafeTest, valueOffset));

		// 通过比较并替换的机制，修改指定偏移量内存的值
		System.out.println(unsafe.compareAndSwapInt(unSafeTest, valueOffset, 0, 1));

		// 获取地址偏移量对应的值
		System.out.println("get:" + unsafe.getIntVolatile(unSafeTest, valueOffset));

		// 通过比较并替换的机制，修改指定偏移量内存的值
		System.out.println(unsafe.compareAndSwapInt(unSafeTest, valueOffset, 1, 2));

		// 获取地址偏移量对应的值
		System.out.println("get:" + unsafe.getIntVolatile(unSafeTest, valueOffset));

		System.out.println(unSafeTest.value);
		
		Thread thread = new Thread(unSafeTest);
		
		// @see java.util.concurrent.locks.LockSupport 
		thread.start();
		
		//LockSupport.park(thread);
		unsafe.park(true,5000L);
		
		//LockSupport.unpark(thread);
		unsafe.unpark(thread);
	}


	@Override
	public void run() {
		while(true){
			System.out.println(Thread.currentThread().getName() + " running......");
		}
	}
}
