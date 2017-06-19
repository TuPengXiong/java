# JVM （java visual machine） 
```
package com.tupengxiong.jvm;

public class Test {
	public static void main(String[] args) {
		// 方法区的常量池中
		String str = "abc";
		// 方法区的常量池中
		String str1 = "abc";
		//new 关键词  堆中开辟新的空间
		String str2 = new String("abc");
		//常量池的地址引用 true
		System.out.println(str == str1);
		//常量池的地址引用 true
		System.out.println(str1 == "abc");
		//堆中的地址引用与常量池的地址引用 false
		System.out.println(str2 == "abc");
		//常量池的地址引用与堆中的地址引用 false
		System.out.println(str1 == str2);
		//内容值比较 true
		System.out.println(str1.equals(str2));
		//常量池的地址引用比较  true
		System.out.println(str1 == str2.intern());
		//堆中的引用地址和常量池的地址引用比较 false
		System.out.println(str2 == str2.intern());
		//比较内容值的hash true
		System.out.println(str1.hashCode() == str2.hashCode());
	}
}

```
