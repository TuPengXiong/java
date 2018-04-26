# java
```
nohup sh mqnamesrv &
nohup sh mqbroker -c /usr/local/apache-rocketmq-all/conf/2m-noslave/broker-a.properties>/dev/null 2>&1 &

```

# mysql 
* 如果一个数据库声称支持事务的操作，那么该数据库必须要具备以下四个特性：

1. 原子性（Atomicity）
1. 一致性（Consistency）　
1. 隔离性（Isolation）
1. 持久性（Durability）

* 四种隔离级别：
1. ① Serializable (串行化)：可避免脏读、不可重复读、幻读的发生。
1. ② Repeatable read (可重复读)：可避免脏读、不可重复读的发生。
1. ③ Read committed (读已提交)：可避免脏读的发生。
1. ④ Read uncommitted (读未提交)：最低级别，任何情况都无法保证。

* 查看 数据库事务隔离
```
SELECT @@tx_isolation;
```
* 设置事务隔离级别
```
set  [glogal | session]  transaction isolation level 隔离级别名称;
```
