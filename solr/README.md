# Apache Solr 
1. Apache Solr 是基于 Apache lucene 的一个开源的搜索服务器。
1. Java 语言编写，Apache Solr 中存储的资源是以 Document 为对象进行存储的。
1. 每个文档由一系列的 Field 构成，每个 Field 表示资源的一个属性。
1. Solr 中的每个 Document 需要有能唯一标识其自身的属性，默认情况下这个属性的名字是 id，在 Schema 配置文件中使用：<uniqueKey>id</uniqueKey>进行描述。

## 安装 [参考](http://lucene.apache.org/solr/guide/6_6/solr-control-script-reference.html#solr-control-script-reference)
*  [官网](http://lucene.apache.org/solr/)
*  [官网下载](http://archive.apache.org/dist/lucene/solr/)

在这里以 http://archive.apache.org/dist/lucene/solr/6.6.0/solr-6.6.0-src.tgz  为例
* 所需环境： JDK1.8+  操作系统：ubuntu
```
cd /usr/local/
wget http://archive.apache.org/dist/lucene/solr/6.6.0/solr-6.6.0-src.tgz
```
1. 解压 获取 install_solr_service.sh
```
tar xzf solr-6.6.0-src.tgz solr-6.6.0/solr/bin/install_solr_service.sh --strip-components=2
```
2. 解压结构+可执行权限
```
bin/install_solr_service.sh

```
## 启动 [参考](http://lucene.apache.org/solr/guide/6_6/solr-control-script-reference.html#solr-control-script-reference)
1. 修改权限
```
chmod +x bin/install_solr_service.sh
```
2. 启动
```
 sudo bash ./bin/install_solr_service.sh solr-6.6.0-src.tgz \
 -i /opt \
 -d /var/solr \
 -u solr \
 -s solr \
 -p 8983
```
3. 执行结果
```
Solr requires java, please install or set JAVA_HOME properly
```
4. 

## 使用 
```
./solr-6.6.0/solr/bin/solr -help

Usage: solr COMMAND OPTIONS
       where COMMAND is one of: start, stop, restart, status, healthcheck, create, create_core, create_collection, delete, version, zk, auth

  Standalone server example (start Solr running in the background on port 8984):

    ./solr start -p 8984

  SolrCloud example (start Solr running in SolrCloud mode using localhost:2181 to connect to Zookeeper, with 1g max heap size and remote Java debug options enabled):

    ./solr start -c -m 1g -z localhost:2181 -a "-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044"

Pass -help after any COMMAND to see command-specific usage information,
  such as:    ./solr start -help or ./solr stop -help

start, stop, restart, status, healthcheck, create, create_core, create_collection, delete, version, zk, auth

```

## 总结
