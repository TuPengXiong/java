# alibaba rocketmq test

[http://rocketmq.apache.org/docs/quick-start/](http://rocketmq.apache.org/docs/quick-start/)

## 修改服务的JAVA_OPT

* 修改mqnamesrv的内存 
```
vim runserver.sh
```
```
默认值 官方认为可靠的配置 
JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g -XX:PermSize=128m -XX:MaxPermSize=320m"

测试所以修改小一点
JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m -XX:PermSize=64m -XX:MaxPermSize=128m"

```
* 修改broker的内存大小 

```
vim runbroker.sh
```
```
默认值 官方认为可靠的配置 
JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g -XX:PermSize=128m -XX:MaxPermSize=320m"

测试所以修改小一点
JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m -XX:PermSize=64m -XX:MaxPermSize=128m"
```
## 启动mqnameserver
```
nohup sh mqnamesrv -n 0.0.0.0:9876 &
``` 
* 查看是否启动 jps
```
# jps 
858 Application
22076 NamesrvStartup
863 Bootstrap
22095 Jps

或 tail -f ~/logs/rocketmqlogs/namesrv.log
2017-10-22 08:12:21 INFO main - serverAsyncSemaphoreValue=64
2017-10-22 08:12:21 INFO main - serverChannelMaxIdleTimeSeconds=120
2017-10-22 08:12:21 INFO main - serverSocketSndBufSize=4096
2017-10-22 08:12:21 INFO main - serverSocketRcvBufSize=4096
2017-10-22 08:12:21 INFO main - serverPooledByteBufAllocatorEnable=true
2017-10-22 08:12:21 INFO main - useEpollNativeSelector=false
2017-10-22 08:12:21 INFO main - The Name Server boot success. serializeType=JSON
2017-10-22 08:12:21 INFO NettyEventExecuter - NettyEventExecuter service started
2017-10-22 08:13:21 INFO NSScheduledThread1 - --------------------------------------------------------
2017-10-22 08:13:21 INFO NSScheduledThread1 - configTable SIZE: 0

```

## 启动mqbroker 
```
nohup sh mqbroker -n 0.0.0.0:9876 autoCreateTopicEnable=true  &

tail -f ~/logs/rocketmqlogs/broker.log 

2017-10-22 08:51:45 INFO BrokerControllerScheduledThread1 - dispatch behind commit log 0 bytes
2017-10-22 08:51:45 INFO BrokerControllerScheduledThread1 - slave fall behind master, how much, 0 bytes
2017-10-22 08:51:45 INFO BrokerControllerScheduledThread1 - register broker to name server 0.0.0.0:9876 OK
```

## 关闭 mqnamesrv mqbroker
```
mqshutdown namesrv
mqshutdown broker
```
