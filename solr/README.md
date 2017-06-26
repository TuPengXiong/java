# Apache Solr 
1. Apache Solr 是基于 Apache lucene 的一个开源的搜索服务器。
1. Java 语言编写，Apache Solr 中存储的资源是以 Document 为对象进行存储的。
1. 每个文档由一系列的 Field 构成，每个 Field 表示资源的一个属性。
1. Solr 中的每个 Document 需要有能唯一标识其自身的属性，默认情况下这个属性的名字是 id，在 Schema 配置文件中使用：<uniqueKey>id</uniqueKey>进行描述。

## 安装 
*  [官网](http://lucene.apache.org/solr/)
*  [官网下载](http://archive.apache.org/dist/lucene/solr/)

在这里以 http://archive.apache.org/dist/lucene/solr/6.6.0/solr-6.6.0-src.tgz  为例
* 所需环境： JDK1.8+  操作系统：ubuntu
```
cd /usr/local/
wget http://archive.apache.org/dist/lucene/solr/6.6.0/solr-6.6.0-src.tgz
```
1. 解压 
```
tar -zvxf solr-6.6.0-src.tgz
```
2. 解压结构
```
drwxr-xr-x  5 root root  4096 Jun 26 16:21 ./
drwxr-xr-x 18 root root  4096 Jun 26 16:21 ../
-rw-r--r--  1 root root 37347 May 30 10:02 build.xml
drwxr-xr-x  9 root root  4096 Jun 26 16:21 dev-tools/
-rw-r--r--  1 root root 12646 May 30 10:02 LICENSE.txt
drwxr-xr-x 28 root root  4096 Jun 26 16:21 lucene/
-rw-r--r--  1 root root 25549 May 30 10:02 NOTICE.txt
-rw-r--r--  1 root root   441 May 30 10:02 README.txt
drwxr-xr-x 15 root root  4096 Jun 26 16:21 solr/
```
## 启动
1. 修改权限(ps： root权限报错)
```
WARNING: Starting Solr as the root user is a security risk and not considered best practice. Exiting.
         Please consult the Reference Guide. To override this check, start with argument '-force'
修改权限 
chown -R solr:solr solr-6.6.0/
```
2. solr 可执行
```
 chmod +x solr-6.6.0/solr/bin/solr
```


## 使用 

## 总结
