## hdfs

### hdfs 是一个分布式文件系统 主要是存储文件使用 包含下面几个模块

* namenode 是接收并处理客户端IO,存储了文件系统所有文件索引,保存在fsname文件中，操作日志存放在edits文件中
* secondNamenode 主要是负责将namenode 所有的文件索引fsname文件 和操作日志edits文件合并产生新的edits 并传输到namenode上
* datanode 主要是存放文件,文件超过指定大小会分为多个datanode, datanode 会根据配置文件的集群数量会备份

## mapreduce

### mapreduce 是一个分布式计算框架 离线式计算 （流式实时计算 storm） 

* map 将input split 后交给map处理 
* map 处理后得到的output 进行partion sort merge 存放在磁盘上
* reduce 将  map merge后的数据提取 进行reduce处理        


## [eclipse plugin](https://github.com/winghc/hadoop2x-eclipse-plugin.git)
```
git clone https://github.com/winghc/hadoop2x-eclipse-plugin.git
cd hadoop2x-eclipse-plugin
cd src/contrib/eclipse-plugin

ant download http://ant.apache.org/bindownload.cgi

ant jar -Dversion=2.9.0 -Dhadoop.version=2.9.0 -Declipse.home=/d/dev/eclipse -Dhadoop.home=/d/dev/hadoop-2.9.0/hadoop-2.9.0

```         

## java win 需要hadoop.dll winutil.exe

* https://github.com/steveloughran/winutil  
                                                                                                                                                        


