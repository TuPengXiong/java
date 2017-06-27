# Apache Solr 
1. Apache Solr 是基于 Apache lucene 的一个开源的搜索服务器。
1. Java 语言编写，Apache Solr 中存储的资源是以 Document 为对象进行存储的。
1. 每个文档由一系列的 Field 构成，每个 Field 表示资源的一个属性。
1. Solr 中的每个 Document 需要有能唯一标识其自身的属性，默认情况下这个属性的名字是 id，在 Schema 配置文件中使用：<uniqueKey>id</uniqueKey>进行描述。

## 安装 [参考](http://lucene.apache.org/solr/guide/6_6/solr-control-script-reference.html#solr-control-script-reference)
*  [官网](http://lucene.apache.org/solr/)
*  [官网下载](http://archive.apache.org/dist/lucene/solr/)

在这里以 http://archive.apache.org/dist/lucene/solr/6.6.0/solr-6.6.0.tgz  为例
* 所需环境： JDK1.8+  操作系统：ubuntu

```
mkdir /usr/local/solr
cd /usr/local/solr/
wget http://archive.apache.org/dist/lucene/solr/6.6.0/solr-6.6.0.tgz
```
1. 解压 获取 install_solr_service.sh

```
 tar xzf solr-6.6.0.tgz solr-6.6.0/bin/install_solr_service.sh --strip-components=2
```
2. 解压结构

```
install_solr_service.sh
```

## 启动 [参考](http://lucene.apache.org/solr/guide/6_6/solr-control-script-reference.html#solr-control-script-reference)
1. 启动
```
 sudo bash ./install_solr_service.sh solr-6.6.0.tgz \
 -i /opt \
 -d /var/solr \
 -u solr \
 -s solr \
 -p 8983
```
2. 执行结果
```
Solr requires java, please install or set JAVA_HOME properly
```
3. solr 用户没有安装JDK 设置所有用户的JDK

```
vim /etc/bash.bashrc 

文件末尾添加
export JAVA_HOME=/usr/local/java/jdk1.8.0_131
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=$JAVA_HOME/bin:$PATH

生效
source /etc/bash.bashrc 
```

5. 重新执行第1步
```
很奇怪 明明在java -version显示了JDK版本信息 但还是报
Solr requires java, please install or set JAVA_HOME properly错误

查看install_solr_service.sh 脚本 没办法 197行注释掉 再执行第1步 成功了（bug?）
```
6.结果
```
sudo service solr status

 Loaded: loaded (/etc/init.d/solr; bad; vendor preset: enabled)
   Active: active (exited) since Tue 2017-06-27 09:54:12 CST; 5min ago
     Docs: man:systemd-sysv-generator(8)
  Process: 11164 ExecStart=/etc/init.d/solr start (code=exited, status=0/SUCCESS

Jun 27 09:53:58 lovesher.com systemd[1]: Starting LSB: Controls Apache Solr as a
Jun 27 09:53:58 lovesher.com su[11168]: Successful su for solr by root
Jun 27 09:53:58 lovesher.com su[11168]: + ??? root:solr
Jun 27 09:53:58 lovesher.com su[11168]: pam_unix(su:session): session opened for
Jun 27 09:54:04 lovesher.com solr[11164]: Warning: Available entropy is low. As
Jun 27 09:54:04 lovesher.com solr[11164]: RNG might not work properly. To check
Jun 27 09:54:12 lovesher.com solr[11164]: [242B blob data]
Jun 27 09:54:12 lovesher.com solr[11164]: Started Solr server on port 8983 (pid=
Jun 27 09:54:12 lovesher.com solr[11164]: [14B blob data]
Jun 27 09:54:12 lovesher.com systemd[1]: Started LSB: Controls Apache Solr as a
```
## 访问 http://host:8983/solr/#/
![界面](http://7xo6kd.com1.z0.glb.clouddn.com/upload-ueditor-image-20170627-1498529196295098305.png)
## 使用 
```
start, stop, restart, status, healthcheck, create, create_core, create_collection, delete, version, zk, auth

```

## 总结
