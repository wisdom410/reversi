数据库用mysql
数据库编码用utf8
数据库内含有一张表(users)

mysql> show tables;
+-------------------+
| Tables_in_reversi |
+-------------------+
| users             |
+-------------------+
1 row in set (0.00 sec)



此数据库中包含4个用户


mysql> select * from users;
+----------+----------------------------------+------+-----------+--------------------+-------+-------+
| username | passwd                           | sex  | nickname  | email              | score | image |
+----------+----------------------------------+------+-----------+--------------------+-------+-------+
| user1    | e10adc3949ba59abbe56e057f20f883e |    2 | NICKNAME1 | USER1@USERS.COM    |     0 |     2 |
| user2    | e10adc3949ba59abbe56e057f20f883e |    1 | nickname2 | user2@users.com    |     0 |     1 |
| sdlwwlp  | 334930f6237bf19668609cf3673fe3f5 |    2 | pisces    | lazydomino@163.com |     0 |     2 |
| user3    | e10adc3949ba59abbe56e057f20f883e |    2 | nickname3 | user3@users.com    |     0 |     2 |
+----------+----------------------------------+------+-----------+--------------------+-------+-------+
4 rows in set (0.00 sec)

user1，user2，user3密码为123456
sdlwwlp密码为888999
密码md5($password)形式
