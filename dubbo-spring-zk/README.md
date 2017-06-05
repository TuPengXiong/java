# dubbo + spring + zookeeper 

* DUBBO是一个分布式服务框架，致力于提供高性能和透明化的RPC远程服务调用方案，是阿里巴巴SOA服务化治理方案的核心框架，[github源码](https://github.com/alibaba/dubbo)

* dubbo 架构 [官方解释]

![dubbo](http://dubbo.io/dubbo-architecture.jpg-version=1&modificationDate=1330892870000.jpg)

上图中，蓝色的表示与业务有交互，绿色的表示只对Dubbo内部交互。上述图所描述的调用流程如下：

1. 服务提供方发布服务到服务注册中心；
1. 服务消费方从服务注册中心订阅服务；
1. 服务消费方调用已经注册的可用服务

# 项目运行
1. 需要将 项目 dubbo-spring-zk-inteface 接口服务项目 上传到本地 maven服务器 给其它两个项目使用 [build 时 加上deploy -e]
1. dubbo-spring-zk-provider 项目是服务提供方
1. dubbo-spring-zk-consumer 项目是服务消费方
1. 采用 zookpeer 协调服务 [docker run -itd -p 2181:2181 --name=zookeper zookeeper] ps:docker 启动zookeeper
1. junit 测试 先启动 dubbo-spring-zk-provider的JunitTestProvider的run() 再启动 dubbo-spring-zk-consumer 的run 

# 项目结果 
1. 启动 dubbo-spring-zk-consumer 的run  完成后，dubbo-spring-zk-provider 会打印 "hello I am consumer" 
1. zookeeper 进入容器查看 ./bin/zkCli.sh  -》 ls  -》 ls dubbo -》 出现了 我们的 GreetingService服务 具体命令
```
# 进入zookeeper容器
docker exec -it zookeeper bash

# 客户端进入zookeeper 
 ./bin/zkCli.sh
 
 #查看节点
 ls /
 结果:[zookeeper,dubbo]
 
 ls /dubbo
 结果:[com.alibaba.dubbo.monitor.MonitorService, org.dubbo.spring.zk.inteface.service.GreetingService]
```

# 坑点
1. 阿里巴巴的dubbo.xsd文件的网址已经不存在了，需要下载到本地，然后再导入到eclipse中

# 工具
1. 管理工具 源码中的 dubbo-admin
1. 监视工具 源码中的 dubbo-monitor

[待续]
