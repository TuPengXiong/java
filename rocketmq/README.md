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

## 接收消息和发送消息
```
export NAMESRV_ADDR=0.0.0.0:9876
sh tools.sh org.apache.rocketmq.example.quickstart.Producer
sh tools.sh org.apache.rocketmq.example.quickstart.Consumer
```
## 关闭 mqnamesrv mqbroker
```
mqshutdown namesrv
mqshutdown broker
```

## mqadmin
```
bash mqadmin 

updateTopic          Update or create topic
   deleteTopic          Delete topic from broker and NameServer.
   updateSubGroup       Update or create subscription group
   deleteSubGroup       Delete subscription group from broker.
   updateBrokerConfig   Update broker's config
   updateTopicPerm      Update topic perm
   topicRoute           Examine topic route info
   topicStatus          Examine topic Status info
   topicClusterList     Get cluster info for topic
   brokerStatus         Fetch broker runtime status data
   queryMsgById         Query Message by Id
   queryMsgByKey        Query Message by Key
   queryMsgByUniqueKey  Query Message by Unique key
   queryMsgByOffset     Query Message by offset
   queryMsgByUniqueKey  Query Message by Unique key
   printMsg             Print Message Detail
   sendMsgStatus        Send msg to broker.
   brokerConsumeStats   Fetch broker consume stats data
   producerConnection   Query producer's socket connection and client version
   consumerConnection   Query consumer's socket connection, client version and subscription
   consumerProgress     Query consumers's progress, speed
   consumerStatus       Query consumer's internal data structure
   cloneGroupOffset     Clone offset from other group.
   clusterList          List all of clusters
   topicList            Fetch all topic list from name server
   updateKvConfig       Create or update KV config.
   deleteKvConfig       Delete KV config.
   wipeWritePerm        Wipe write perm of broker in all name server
   resetOffsetByTime    Reset consumer offset by timestamp(without client restart).
   updateOrderConf      Create or update or delete order conf
   cleanExpiredCQ       Clean expired ConsumeQueue on broker.
   cleanUnusedTopic     Clean unused topic on broker.
   startMonitoring      Start Monitoring
   statsAll             Topic and Consumer tps stats
   syncDocs             Synchronize wiki and issue to github.com
   allocateMQ           Allocate MQ
   checkMsgSendRT       Check message send response time
   clusterRT            List All clusters Message Send RT

```
