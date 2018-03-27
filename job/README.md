http://mesos.apache.org/documentation/latest/building/

# Install a recent kernel for full support of process isolation.
```
$ sudo rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org
$ sudo rpm -Uvh http://www.elrepo.org/elrepo-release-6-6.el6.elrepo.noarch.rpm
$ sudo yum --enablerepo=elrepo-kernel install -y kernel-lt
```
# Make the just installed kernel the one booted by default, and reboot.
```
$ sudo sed -i 's/default=1/default=0/g' /boot/grub/grub.conf
$ sudo reboot
```
# Install a few utility tools. This also forces an update of `nss`,
# which is necessary for the Java bindings to build properly.
```
$ sudo yum install -y tar wget git which nss
```

# 'Mesos > 0.21.0' requires a C++ compiler with full C++11 support,
# (e.g. GCC > 4.8) which is available via 'devtoolset-2'.
# Fetch the Scientific Linux CERN devtoolset repo file.
```
$ sudo wget -O /etc/yum.repos.d/slc6-devtoolset.repo http://linuxsoft.cern.ch/cern/devtoolset/slc6-devtoolset.repo
```
# Import the CERN GPG key.
```
$ sudo rpm --import http://linuxsoft.cern.ch/cern/centos/7/os/x86_64/RPM-GPG-KEY-cern
```
# Fetch the Apache Maven repo file.
```
$ sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
```

# 'Mesos > 0.21.0' requires 'subversion > 1.8' devel package, which is
# not available in the default repositories.
# Create a WANdisco SVN repo file to install the correct version:
```
$ sudo bash -c 'cat > /etc/yum.repos.d/wandisco-svn.repo <<EOF
[WANdiscoSVN]
name=WANdisco SVN Repo 1.8
enabled=1
baseurl=http://opensource.wandisco.com/centos/6/svn-1.8/RPMS/$basearch/
gpgcheck=1
gpgkey=http://opensource.wandisco.com/RPM-GPG-KEY-WANdisco
EOF'
```
# Install essential development tools.
```
$ sudo yum groupinstall -y "Development Tools"
```
# Install 'devtoolset-2-toolchain' which includes GCC 4.8.2 and related packages.
```
$ sudo yum install -y devtoolset-2-toolchain
```
# Install other Mesos dependencies.
```
$ sudo yum install -y apache-maven python-devel python-six python-virtualenv java-1.7.0-openjdk-devel zlib-devel libcurl-devel openssl-devel cyrus-sasl-devel cyrus-sasl-md5 apr-devel subversion-devel apr-util-devel
```
# Enter a shell with 'devtoolset-2' enabled.
```
$ scl enable devtoolset-2 bash
$ g++ --version  # Make sure you've got GCC > 4.8!
```
# Process isolation is using cgroups that are managed by 'cgconfig'.
# The 'cgconfig' service is not started by default on CentOS 6.6.
# Also the default configuration does not attach the 'perf_event' subsystem.
# To do this, add 'perf_event = /cgroup/perf_event;' to the entries in '/etc/cgconfig.conf'.
```
$ sudo yum install -y libcgroup
$ sudo service cgconfig start
```

## Building Mesos 
# Change working directory.
```
$ cd mesos
```
# Bootstrap (Only required if building from git repository).
```
$ ./bootstrap
```
# Configure and build.
```
$ mkdir build
$ cd build
$ ../configure
$ make
$ make install 
```

# elastic-job-schedule
```
git clone https://github.com/elasticjob/elastic-job-lite.git
git checkout 2.1.5
mvn clean install -Dmaven.test.skip=true
```
* nginx开启
```
systemctl start nginx
```
* 上传job-1.0-SNAPSHOT.tar.gz 到/usr/local/目录下
```
rz job-1.0-SNAPSHOT.tar.gz
```

* 时间同步
```
ntpdate cn.pool.ntp.org
hwclock --systohc
date
```
* 防火墙
```
service iptables status
service iptables stop
```

* 开启zookeeper
```
/usr/local/zookeeper-3.4.10/bin/zkServer.sh start
```
* 开启mesos master slaver or agent
```
/usr/local/mesos-1.1.0/build/bin/mesos-master.sh --ip=192.168.0.67 --work_dir=/var/lib/mesos --zk='zk://192.168.0.67:2181/mesos' --quorum=1  --log_dir=/var/lib/mesos/master/master.log
```
```
/usr/local/mesos-1.1.0/build/bin/mesos-agent.sh --master=192.168.0.67:5050 --work_dir=/var/lib/mesos/agent
/usr/local/mesos-1.1.0/build/bin/mesos-slave.sh --master=192.168.0.67:5050 --work_dir=/var/lib/mesos/slave
```
* 开启scheduler
```
/usr/local/elastic-job-cloud-scheduler-2.1.5/bin/start.sh
```
* 注册app
```
curl -l -H "Content-type: application/json" -X POST -d '{"appName":"job-app","appURL":"http://192.168.0.67/local/job-1.0-SNAPSHOT.tar.gz","cpuCount":0.1,"memoryMB":64.0,"bootstrapScript":"bin/start.sh","appCacheEnable":true}' http://127.0.0.1:8899/api/app
```
* 注册任务

1. simple
```
curl -l -H "Content-type: application/json" -X POST -d '{"jobName":"job_simple_spring","appName":"job-app","jobType":"SIMPLE","jobExecutionType":"TRANSIENT","jobClass":"com.aidai.job.simple.TaskSimpleJob","beanName":"taskSimpleJob","applicationContext":"classpath:/applicationContext.xml","cron":"0/10 * * * * ?","shardingTotalCount":1,"cpuCount":0.1,"memoryMB":64.0}' http://localhost:8899/api/job/register
```
1. dataflow
```
curl -l -H "Content-type: application/json" -X POST -d '{"jobName":"job_dataflow_spring","appName":"job-app","jobType":"DATAFLOW","jobExecutionType":"DAEMON","jobClass":"com.aidai.job.dataflow.TaskDataFlowJob","beanName":"taskDataFlowJob","applicationContext":"classpath:/applicationContext.xml","cron":"0/20 * * * * ?","shardingTotalCount":1,"cpuCount":0.1,"memoryMB":64.0}' http://localhost:8899/api/job/register
```
