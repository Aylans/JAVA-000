### 建表语句
```
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别：1男 2女',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1有效 0失效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';
```

```
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品主键',
  `name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1有效 0失效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品';
```
```
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '订单数量',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1有效 0失效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间戳',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间戳',
  PRIMARY KEY (`id`),
  KEY `order_user_id_IDX` (`user_id`) USING BTREE,
  KEY `order_product_id_IDX` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单';
```

### 写入语句
```
/*
insert into users (id,name,age,gender) values (1,'张三',12,1),(3,'李四',18,2);
insert into product (id,name) values (1,'桌子'),(3,'椅子');
insert into orders (amount,user_id,product_id) values (3,1,1),(3,1,3),(5,3,3),(5,3,3);
*/
```

### 查询语句
```
select * from users;
select * from product;
select * from orders;
```

### 清空语句
```
/*
truncate table users;
truncate table product; 
truncate table orders;
*/
```