# Redis高可用/Redisson/Hazelcast

## 主从复制

### 设置详情

```bash
# 已知网关IP为：172.17.0.1

# 启动master节点
docker run -it --name redis-6380  -p 6380:6379 redis
docker exec -it redis-6380 /bin/bash
redis-cli -h 172.17.0.1 -p 6380
# 或执行： redis-cli

# 启动slave节点 1
docker run -it --name redis-6381  -p 6381:6379 redis
docker exec -it redis-6381 /bin/bash
redis-cli -h 172.17.0.1 -p 6381
replicaof 172.17.0.1 6380

# 启动slave节点 2
docker run -it --name redis-6382  -p 6382:6379 redis
docker exec -it redis-6382 /bin/bash
redis-cli -h 172.17.0.1 -p 6382
replicaof 172.17.0.1 6380
```

之后可查看 master 节点的信息，在master-redis下，执行：

```bash
> info Replication

# Replication
role:master
connected_slaves:2
slave0:ip=172.17.0.1,port=6379,state=online,offset=686,lag=0
slave1:ip=172.17.0.1,port=6379,state=online,offset=686,lag=1
master_replid:79187e2241015c2f8ed98ce68caafa765796dff2
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:686
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:686
```

之后操作master节点，slave节点会自动同步。

slave-redis下执行`replicaof no one`可重新改为主节点

### 关键点

1. 查看网络相关信息

```bash
docker network ls
docker network inspect bridge
```

2. 容器之间互访问，可使用内部端口号，也可使用外部映射端口号

3. 执行`docker network inspect bridge`后，可查看到网关IP以及各容器IP，可使用网关IP:外部映射端口，或容器IP:6379访问Redis

### 参考资料

1. [命令：SLAVEOF](https://www.redis.com.cn/commands/slaveof.html)

## sentinel 高可用

当前状态：
1. 网关IP：172.17.0.1
2. master端口：6390
3. slave端口：6391，6392

### 操作步骤
1. 重新创建redis的docker容器

redis.conf配置内容如下：
```bash
#默认端口6379
port 6390
#绑定ip，如果是内网可以直接绑定 127.0.0.1, 或者忽略, 0.0.0.0是外网
bind 0.0.0.0
#守护进程启动
daemonize no
```

变更监听端口号，并重新创建redis容器：
```bash
docker run -p 6390:6390 -v D:\develop\shell\docker\redis\conf6390:/usr/local/etc/redis --name redis-conf-6390 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6390 /bin/bash
redis-cli -h 172.17.0.1 -p 6390

docker run -p 6391:6391 -v D:\develop\shell\docker\redis\conf6391:/usr/local/etc/redis --name redis-conf-6391 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6391 /bin/bash
redis-cli -h 172.17.0.1 -p 6391
slaveof 172.17.0.1 6390

docker run -p 6392:6392 -v D:\develop\shell\docker\redis\conf6392:/usr/local/etc/redis --name redis-conf-6392 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6392 /bin/bash
redis-cli -h 172.17.0.1 -p 6392
slaveof 172.17.0.1 6390
```

之后可查看 master 节点的信息，可看到master获取到的slave的端口号恢复了正常。在master-redis下，执行：

```bash
> info Replication

# Replication
role:master
connected_slaves:2
slave0:ip=172.17.0.1,port=6391,state=online,offset=84,lag=0
slave1:ip=172.17.0.1,port=6392,state=online,offset=84,lag=0
master_replid:ed2e513ceed2b48a272b97c674c99d82284342a1
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:84
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:84
```

2. 创建配置文件

创建`sentinel.conf`，文件中写入如下内容：

```
sentinel monitor bitkylin-master 172.17.0.1 6390 2
sentinel down-after-milliseconds bitkylin-master 5000
sentinel failover-timeout bitkylin-master 10000
sentinel parallel-syncs bitkylin-master 1
```

命令详解：指示 Sentinel 去监视一个名为 bitkylin-master 的主服务器，将这个主服务器标记为客观下线至少需要 2 个 Sentinel 同意；
响应超时5秒标记为主观下线，主观下线后就开始了迁移流程，超时10秒为迁移超时，暂不知用途。

3. 再创建两个redis-docker容器，将配置文件复制到docker容器内，共两个容器需要复制该文件：
```bash
docker run -it --name redis-6490 redis
docker run -it --name redis-6491 redis
docker cp ./sentinel.conf dcbd015dbc0e:/data/sentinel.conf
docker cp ./sentinel.conf 7c8307730bcc:/data/sentinel.conf
```

3. 执行命令，执行 redis-sentinel
```bash
 redis-sentinel sentinel.conf
```

4. 此时任意启停redis容器，可以看到sentinel自动完成redis的主从切换，主从配置等不需要人工操作

### 参考资料

1. [Redis 的 Sentinel 文档](http://www.redis.cn/topics/sentinel.html)
2. [Docker容器的文件操作](https://blog.csdn.net/weixin_43288858/article/details/104597500)
3. `> 覆盖写入; >> 追加写入`

## Cluster 集群

1. 更新redis配置文件

主要追加集群配置信息，示例配置文件如下：
```bash
#默认端口6379
port 6390
#绑定ip，如果是内网可以直接绑定 127.0.0.1, 或者忽略, 0.0.0.0是外网
bind 0.0.0.0
#守护进程启动
daemonize no
#集群配置
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
```

2. 以第二节作为基础，基于最新的配置文件，创建6个容器，注意新增集群总线端口映射：

```bash
docker run -p 6390:6390 -p 16390:16390 -v D:\develop\shell\docker\redis\conf6390:/usr/local/etc/redis --name redis-conf-6390 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6390 /bin/bash
redis-cli -h 172.17.0.1 -p 6390

docker run -p 6391:6391 -p 16391:16391 -v D:\develop\shell\docker\redis\conf6391:/usr/local/etc/redis --name redis-conf-6391 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6391 /bin/bash
redis-cli -h 172.17.0.1 -p 6391

docker run -p 6392:6392 -p 16392:16392 -v D:\develop\shell\docker\redis\conf6392:/usr/local/etc/redis --name redis-conf-6392 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6392 /bin/bash
redis-cli -h 172.17.0.1 -p 6392

docker run -p 6393:6393 -p 16393:16393 -v D:\develop\shell\docker\redis\conf6393:/usr/local/etc/redis --name redis-conf-6393 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6393 /bin/bash
redis-cli -h 172.17.0.1 -p 6393

docker run -p 6394:6394 -p 16394:16394 -v D:\develop\shell\docker\redis\conf6394:/usr/local/etc/redis --name redis-conf-6394 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6394 /bin/bash
redis-cli -h 172.17.0.1 -p 6394

docker run -p 6395:6395 -p 16395:16395 -v D:\develop\shell\docker\redis\conf6395:/usr/local/etc/redis --name redis-conf-6395 redis redis-server /usr/local/etc/redis/redis.conf
docker exec -it redis-conf-6395 /bin/bash
redis-cli -h 172.17.0.1 -p 6395
```

2. 直接通过命令创建集群
```bash
> redis-cli --cluster create 172.17.0.1:6390 172.17.0.1:6391 172.17.0.1:6392 172.17.0.1:6393 172.17.0.1:6394 172.17.0.1:6395 --cluster-replicas 1

# 以下是命令执行结果：

>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 172.17.0.1:6394 to 172.17.0.1:6390
Adding replica 172.17.0.1:6395 to 172.17.0.1:6391
Adding replica 172.17.0.1:6393 to 172.17.0.1:6392
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: a9678b062663957e59bc3b4beb7be4366fa24adc 172.17.0.1:6390
   slots:[0-5460] (5461 slots) master
M: 41a4976431713cce936220fba8a230627d28d40c 172.17.0.1:6391
   slots:[5461-10922] (5462 slots) master
M: 1bf83414a12bad8f2e25dcea19ccea1c881d28c5 172.17.0.1:6392
   slots:[10923-16383] (5461 slots) master
S: 3d65eadd3321ef34c9413ae8f75d610c4228eda7 172.17.0.1:6393
   replicates 41a4976431713cce936220fba8a230627d28d40c
S: b604356698a5f211823ada4b45a97939744b1d57 172.17.0.1:6394
   replicates 1bf83414a12bad8f2e25dcea19ccea1c881d28c5
S: 2c1cc93221dc3830aa1eb28601ac27e22a6801cc 172.17.0.1:6395
   replicates a9678b062663957e59bc3b4beb7be4366fa24adc
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
.
>>> Performing Cluster Check (using node 172.17.0.1:6390)
M: a9678b062663957e59bc3b4beb7be4366fa24adc 172.17.0.1:6390
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: b604356698a5f211823ada4b45a97939744b1d57 172.17.0.1:6394
   slots: (0 slots) slave
   replicates 1bf83414a12bad8f2e25dcea19ccea1c881d28c5
M: 41a4976431713cce936220fba8a230627d28d40c 172.17.0.1:6391
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: 3d65eadd3321ef34c9413ae8f75d610c4228eda7 172.17.0.1:6393
   slots: (0 slots) slave
   replicates 41a4976431713cce936220fba8a230627d28d40c
M: 1bf83414a12bad8f2e25dcea19ccea1c881d28c5 172.17.0.1:6392
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 2c1cc93221dc3830aa1eb28601ac27e22a6801cc 172.17.0.1:6395
   slots: (0 slots) slave
   replicates a9678b062663957e59bc3b4beb7be4366fa24adc
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

集群创建成功

### 注意点
1. 需要开放集群总线端口号，默认为业务端口号 + 10000
2. `cluster reset`命令可以将当前节点从集群中移除

### 参考资料
1. [redis-cluster集群--安装与状态验证](https://www.jianshu.com/p/fcf24fe0493d)
2. [Redis 集群教程](https://www.redis.com.cn/topics/cluster-tutorial.html)
3. [Redis 命令参考 - 集群教程](http://redisdoc.com/topic/cluster-tutorial.html)