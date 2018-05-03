# java-动态代理-jdk代理、cglib代理、生成字节码文件.


## JDK动态代理和CGLIB字节码生成的区别？
 * JDK动态代理只能对实现了接口的类生成代理，而不能针对类
 * CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
   因为是继承，所以该类或方法最好不要声明成final 


## 静态代理、动态代理区别？
静态代理：由程序员创建或工具生成代理类的源码，再编译代理类，即代理类和委托类的关系再程序运行前就已经存在。
动态代理：在运行期间使用动态生成字节码形式，动态创建代理类。使用的工具有 jdkproxy、cglibproxy 等。


## aop、拦截器、代理之间的关系？
aop:面向切面编程，有一系列的规范和配置;
拦截器：一种机制，简单理解就是在待“执行的方法”的前后各调用一个方法，before()、after();
代理：在不破坏原有类方法封装的前提下，使用代理模式调用原有类的方法，在执行方法前调用 before() ,执行后调用 after();
之间关系：要实现 aop 需要使用代理模式，实现代理模式就需要用到拦截器；


## Aspect

### AspectJ切入点指示符如下：
* execution：用于匹配方法执行的连接点；
* within：用于匹配指定类型内的方法执行；
* this：用于匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配；
* target：用于匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；
* args：用于匹配当前执行的方法传入的参数为指定类型的执行方法；
* @within：用于匹配所以持有指定注解类型内的方法；
* @target：用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；
* @args：用于匹配当前执行的方法传入的参数持有指定注解的执行；
* @annotation：用于匹配当前执行方法持有指定注解的方法；
* bean：Spring AOP扩展的，AspectJ没有对于指示符，用于匹配特定名称的Bean对象的执行方法；
* reference pointcut：表示引用其他命名切入点，只有@ApectJ风格支持，Schema风格不支持。

### AspectJ切入点支持的切入点指示符还有： 
* call、get、set、preinitialization、staticinitialization、initialization、handler、adviceexecution、withincode、cflow、cflowbelow、if、@this、@withincode；但Spring AOP目前不支持这些指示符，使用这些指示符将抛出IllegalArgumentException异常。这些指示符Spring AOP可能会在以后进行扩展。