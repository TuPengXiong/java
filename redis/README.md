# redis 集群 ubuntu
## redis 下载
1. [官网下载链接](https://redis.io/download)
1. 版本号 4.0.1

## 解压 编译
```
tar -zxvf redis-4.0.1.tar.gz
cd redis-4.0.1
make

cd src
```
## redis的配置文件 
1. 新建一个文件夹 专门存配置文件
```
mkdir redisConf
cd redisConf
```
2. redis.conf 每一个端口建立一个文件夹 以6379端口为例 
```
mkdir 6379
cd 6379
vim 6379.conf
```
3. 6379.conf 添加下面配置
```
#  Redis默认不是以守护进程的方式运行，可以通过该配置项修改，使用yes启用守护进程
daemonize yes
#  当Redis以守护进程方式运行时，Redis默认会把pid写入/var/run/redis.pid文件，可以通过pidfile指定
pidfile /home/frist-tpx/develop/redisConf/6379/redis.pid
# 端口号
port 6379
# 绑定的主机地址
bind 0.0.0.0
# 当 客户端闲置多长时间后关闭连接，如果指定为0，表示关闭该功能
timeout 0
# 指定日志记录级别，Redis总共支持四个级别：debug、verbose、notice、warning，默认为verbose
loglevel verbose
# 日志记录方式，默认为标准输出，如果配置Redis为守护进程方式运行，
# 而这里又配置为日志记录方式为标准输出，则日志将会发送给/dev/null
logfile stdout
#logfile /home/frist-tpx/develop/redisConf/6379/redis.log
# 设置数据库的数量，默认数据库为0，可以使用SELECT <dbid>命令在连接上指定数据库id
databases 16
# 指定在多长时间内，有多少次更新操作，就将数据同步到数据文件，可以多个条件配合
save 900 1
save 300 10
save 60 10000
# 指定存储至本地数据库时是否压缩数据，默认为yes，Redis采用LZF压缩，如果为了节省CPU时间，可以关闭该选项，但会导致数据库文件变的巨大
rdbcompression yes

# 指定本地数据库文件名，默认值为dump.rdb
dbfilename dump6379.rdb
# 指定本地数据库存放目录
dir ./
# 设置当本机为slav服务时，设置master服务的IP地址及端口，在Redis启动时，它会自动从master进行数据同步
# slaveof 127.0.0.1 6379
# 当master服务设置了密码保护时，slav服务连接master的密码
# masterauth redis
# 设置Redis连接密码，如果配置了连接密码，客户端在连接Redis时需要通过AUTH <password>命令提供密码，默认关闭
#requirepass redis
# 设置同一时间最大客户端连接数，默认无限制，Redis可以同时打开的客户端连接数为Redis进程可以打开的最大文件描述符数，
# 如果设置 maxclients 0，表示不作限制。当客户端连接数到达限制时，Redis会关闭新的连接并向客户端返回max number of clients reached错误信息
maxclients 128

# 开启集群
cluster-enabled yes
# nodes.conf 配置文件 
cluster-config-file /home/frist-tpx/develop/redisConf/6379/nodes.conf
# 结点之间时间
cluster-node-timeout 5000

```

4. 重复2,3步骤配置6380，6381，6382，6383，6384端口号,并修改 conf
```
port 端口号
dbfilename 本地数据库文件名
nodes.conf 配置文件
pidfile 进程文件的位置
logfile 日志的配置文件
```

## 启动 redis-server 回到redis-4.0.1/src目录下
```
./redis-server ./redisConf/6379/6379.conf
./redis-server ./redisConf/6380/6380.conf
./redis-server ./redisConf/6381/6381.conf
./redis-server ./redisConf/6382/6382.conf
./redis-server ./redisConf/6383/6383.conf
./redis-server ./redisConf/6384/6384.conf

# 查看redis 服务启动是否
ps -ef|grep redis
frist-t+   9342      1  0 13:07 ?        00:00:07 ./redis-server 0.0.0.0:6379 [cluster]
frist-t+   9347      1  0 13:07 ?        00:00:07 ./redis-server 0.0.0.0:6380 [cluster]
frist-t+   9352      1  0 13:07 ?        00:00:06 ./redis-server 0.0.0.0:6381 [cluster]
frist-t+   9357      1  0 13:07 ?        00:00:07 ./redis-server 0.0.0.0:6382 [cluster]
frist-t+   9362      1  0 13:07 ?        00:00:07 ./redis-server 0.0.0.0:6383 [cluster]
frist-t+   9367      1  0 13:07 ?        00:00:07 ./redis-server 0.0.0.0:6384 [cluster]
```

## 至此 redis 各服务已经开启，集群还没有完全建立，还需用到 redis-trib.rb ruby写的一个脚本
```
# 安装ruby 安装过请略过
sudo apt install ruby
# redis 的依赖包
sudo gem install redis
# 集群创建 1主1从 集群必须最少3主结点 
./redis-trib.rb create --replicas 1 127.0.0.1:6379 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6384

# 打印信息
Using 3 masters:
127.0.0.1:6379
127.0.0.1:6380
127.0.0.1:6381
Adding replica 127.0.0.1:6382 to 127.0.0.1:6379
Adding replica 127.0.0.1:6383 to 127.0.0.1:6380
Adding replica 127.0.0.1:6384 to 127.0.0.1:6381
M: 9c94103f95fef075b3c0b3d393defa200df9cf6f 127.0.0.1:6379
   slots:0-5460 (5461 slots) master
M: f389aa4265e73a01a305a919a4adb8f9bc17fe40 127.0.0.1:6380
   slots:5461-10922 (5462 slots) master
M: c1f3555df76b1390374d5a2f921efcb015d085da 127.0.0.1:6381
   slots:10923-16383 (5461 slots) master
S: d539a1566b0293001cce3d9c8bbd5558adb81978 127.0.0.1:6382
   replicates 9c94103f95fef075b3c0b3d393defa200df9cf6f
S: 95f110db6c7cdc7d4765bbd06daad5ff9457fc1f 127.0.0.1:6383
   replicates f389aa4265e73a01a305a919a4adb8f9bc17fe40
S: 70ab10aacb9eb8a2eefcfc5a44eb39a1becbc419 127.0.0.1:6384
   replicates c1f3555df76b1390374d5a2f921efcb015d085da
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join...
>>> Performing Cluster Check (using node 127.0.0.1:6379)
M: 9c94103f95fef075b3c0b3d393defa200df9cf6f 127.0.0.1:6379
   slots:0-5460 (5461 slots) master
   1 additional replica(s)
M: f389aa4265e73a01a305a919a4adb8f9bc17fe40 127.0.0.1:6380
   slots:5461-10922 (5462 slots) master
   1 additional replica(s)
M: c1f3555df76b1390374d5a2f921efcb015d085da 127.0.0.1:6381
   slots:10923-16383 (5461 slots) master
   1 additional replica(s)
S: 70ab10aacb9eb8a2eefcfc5a44eb39a1becbc419 127.0.0.1:6384
   slots: (0 slots) slave
   replicates c1f3555df76b1390374d5a2f921efcb015d085da
S: 95f110db6c7cdc7d4765bbd06daad5ff9457fc1f 127.0.0.1:6383
   slots: (0 slots) slave
   replicates f389aa4265e73a01a305a919a4adb8f9bc17fe40
S: d539a1566b0293001cce3d9c8bbd5558adb81978 127.0.0.1:6382
   slots: (0 slots) slave
   replicates 9c94103f95fef075b3c0b3d393defa200df9cf6f
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```
## 集群检验和分片处理
```
## 集群检验
./redis-trib.rb check 127.0.0.1:6379
# 日志
M: f389aa4265e73a01a305a919a4adb8f9bc17fe40 127.0.0.1:6380
   slots:5461-10922 (5462 slots) master
   1 additional replica(s)
S: 95f110db6c7cdc7d4765bbd06daad5ff9457fc1f 127.0.0.1:6383
   slots: (0 slots) slave
   replicates f389aa4265e73a01a305a919a4adb8f9bc17fe40
M: 9c94103f95fef075b3c0b3d393defa200df9cf6f 127.0.0.1:6379
   slots:0-5460 (5461 slots) master
   1 additional replica(s)
S: 70ab10aacb9eb8a2eefcfc5a44eb39a1becbc419 127.0.0.1:6384
   slots: (0 slots) slave
   replicates c1f3555df76b1390374d5a2f921efcb015d085da
M: c1f3555df76b1390374d5a2f921efcb015d085da 127.0.0.1:6381
   slots:10923-16383 (5461 slots) master
   1 additional replica(s)
S: d539a1566b0293001cce3d9c8bbd5558adb81978 127.0.0.1:6382
   slots: (0 slots) slave
   replicates 9c94103f95fef075b3c0b3d393defa200df9cf6f


## 集群修复
./redis-trib.rb fix 127.0.0.1:6380
# 错误
[ERR] Not all 16384 slots are covered by nodes.
提示输入 yes
```

## redis-trib.rb 命令
```
./redis-trib.rb help
Usage: redis-trib <command> <options> <arguments ...>

  create          host1:port1 ... hostN:portN
                  --replicas <arg>
  check           host:port
  info            host:port
  fix             host:port
                  --timeout <arg>
  reshard         host:port
                  --from <arg>
                  --to <arg>
                  --slots <arg>
                  --yes
                  --timeout <arg>
                  --pipeline <arg>
  rebalance       host:port
                  --weight <arg>
                  --auto-weights
                  --use-empty-masters
                  --timeout <arg>
                  --simulate
                  --pipeline <arg>
                  --threshold <arg>
  add-node        new_host:new_port existing_host:existing_port
                  --slave
                  --master-id <arg>
  del-node        host:port node_id
  set-timeout     host:port milliseconds
  call            host:port command arg arg .. arg
  import          host:port
                  --from <arg>
                  --copy
                  --replace
  help            (show this help)

For check, fix, reshard, del-node, set-timeout you can specify the host and port of any working node in the cluster.

```
## redis分片  16384被分为3 份 存储在不同的redis服务上
```
./redis-cli -c -p  6379 
127.0.0.1:6379> get 1
-> Redirected to slot [9842] located at 127.0.0.1:6380
"1"

# -c 跳转
```




