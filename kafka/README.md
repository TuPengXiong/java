# kafka [http://kafka.apache.org/documentation/#uses](http://kafka.apache.org/documentation/#uses)

## kafka [download-2.11](https://www.apache.org/dyn/closer.cgi?path=/kafka/1.0.0/kafka_2.11-1.0.0.tgz)

## 如果没有zookeeper节点 开启一个单节点的zookeeper

```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
## 开启Kafka server(broker)

```
bin/kafka-server-start.sh config/server.properties
```
## 创建一个TOPIC

```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

## 查看TOPIC
```
 bin/kafka-topics.sh --list --zookeeper localhost:2181
 test
```

## 发送消息
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
1
2
```

## 开启一个CONSUMER
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

1
2
```

## 设置多个broker
```
cp config/server.properties config/server1.properties 
cp config/server.properties config/server2.properties 

config/server-1.properties:
    broker.id=1
    listeners=PLAINTEXT://:9093
    log.dir=/tmp/kafka-logs-1
 
config/server-2.properties:
    broker.id=2
    listeners=PLAINTEXT://:9094
    log.dir=/tmp/kafka-logs-2
    
bin/kafka-server-start.sh config/server1.properties &
bin/kafka-server-start.sh config/server2.properties &
```

## 集群TOPIC
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic my-replicated-topic
-- 查看
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-replicated-topic
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic test
```

## 关闭broker进程
```
> ps aux | grep server-1.properties
7564 ttys002    0:15.91 /System/Library/Frameworks/JavaVM.framework/Versions/1.8/Home/bin/java...
> kill -9 7564
```
